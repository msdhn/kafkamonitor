package eu.msdhn.kafkamonitor.client;

import eu.msdhn.kafkamonitor.domain.KafkaBroker;
import eu.msdhn.kafkamonitor.domain.KafkaEndPoint;
import eu.msdhn.kafkamonitor.domain.KafkaTopic;
import eu.msdhn.kafkamonitor.domain.kafkaTopicPartition;
import kafka.server.ConfigType;
import kafka.zk.AdminZkClient;
import kafka.zk.KafkaZkClient;
import lombok.Getter;
import lombok.val;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.utils.Time;
import scala.Int;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static scala.collection.JavaConverters.asJavaCollection;

/**
 * N.B : connection must be closed carefully
 */
class KafkaClient {

    protected AdminZkClient adminZkClient;
    private KafkaZkClient zkClient;
    private int sessionTimeOutMS = 30000;
    private int connectionTimeOutMS = 30000;

    @Getter
    private String zookeeper;

    public KafkaClient(String zooKeeperString) {
        this.zookeeper = zooKeeperString;
        this.zkClient = KafkaZkClient.apply("localhost:2181", JaasUtils.isZkSecurityEnabled(), sessionTimeOutMS, connectionTimeOutMS, Int.MaxValue(), Time.SYSTEM, "", "");
        this.adminZkClient = new AdminZkClient(this.zkClient);
    }

    public void close() {
        this.zkClient.close();
    }

    public List<KafkaBroker> getAllBrokersInCluster() {
        val brokerList = this.zkClient.getAllBrokersInCluster();
        return asJavaCollection(brokerList).stream().map(b -> {
            String rack = b.rack().isDefined() ? b.rack().get() : "undefined";
            val kafkaBroker = new KafkaBroker("" + b.id(), "", rack);
            kafkaBroker.setEndPoints(
                    asJavaCollection(b.endPoints()).stream().map(
                            p -> {
                                val kafkaEndPoint = new KafkaEndPoint();
                                kafkaEndPoint.setHost(p.host());
                                kafkaEndPoint.setPort(p.port());
                                return kafkaEndPoint;
                            }
                    ).collect(Collectors.toSet())
            );
            kafkaBroker.setTopicPartitions(getPartitionsOnABroker(kafkaBroker.getId()));

            return kafkaBroker;
        }).collect(Collectors.toList());
    }

    public List<kafkaTopicPartition> getPartitionsOnABroker(String brokerId) {

        val allTopicPartitions = asJavaCollection(this.zkClient.getAllPartitions()).stream()
                .map(eachPartition -> {
                    kafkaTopicPartition kafkaTopicPartition = new kafkaTopicPartition();
                    kafkaTopicPartition.setPartitionNumber(eachPartition.partition() + "");
                    kafkaTopicPartition.setTopic(new KafkaTopic(eachPartition.topic()));

                    kafkaTopicPartition.setReplicas(
                            asJavaCollection(this.zkClient.getReplicasForPartition(eachPartition)).stream().map(eachReplica -> {
                                val kafkaBroker = new KafkaBroker((Integer) eachReplica + "");
                                return kafkaBroker;
                            }).collect(Collectors.toList()));


                    return kafkaTopicPartition;
                }).filter(kafkaTopicPartition -> kafkaTopicPartition.getReplicas().stream().map(p -> p.getId()).collect(Collectors.toList()).contains(brokerId))
                .collect(Collectors.toList());

        return allTopicPartitions;
    }

    public List<KafkaTopic> getTopics() {

        val allTopics = this.zkClient.getAllTopicsInCluster();
        return asJavaCollection(allTopics).stream()
                .map(topicName -> {
                    val kafkaTopic = new KafkaTopic(topicName);
                    kafkaTopic.setInternal(Topic.isInternal(topicName));
                    val nop = Optional.ofNullable(this.zkClient.getTopicPartitionCount(topicName).getOrElse(null));
                    kafkaTopic.setPartitions(nop.isPresent() ? (Integer) nop.get() : 0);
                    kafkaTopic.setMarkedForDeletion(this.zkClient.isTopicMarkedForDeletion(topicName));

                    //TODO : need to check is this works (does it give the unavailable replicas as well)
                    kafkaTopic.setReplicationFactor(getReplicationFactor(topicName));

                    kafkaTopic.setConfig(this.adminZkClient.fetchEntityConfig(ConfigType.Topic(), topicName));

                    return kafkaTopic;
                }).collect(Collectors.toList());
    }

    //TODO : need to check is this works (does it give the unavailable replicas as well)
    private int getReplicationFactor(String topicName) {
        val replicationFactor = this.zkClient.getReplicasForPartition(new TopicPartition(topicName, 0)).size();
        return replicationFactor;
    }
}
