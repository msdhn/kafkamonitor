package eu.msdhn.kafkamonitor.client;

import eu.msdhn.kafkamonitor.domain.KafkaBroker;
import eu.msdhn.kafkamonitor.domain.KafkaCluster;
import eu.msdhn.kafkamonitor.domain.KafkaTopic;
import lombok.val;

import java.util.Comparator;
import java.util.List;

public class KafkaClusterInfoBuilder {

    private KafkaClient kafkaClient;

    private KafkaClusterInfoBuilder(String zooKeeper) {
        this.kafkaClient = new KafkaClient(zooKeeper);
    }

    public static KafkaClusterInfoBuilder instance(String zooKeeper) {
        return new KafkaClusterInfoBuilder(zooKeeper);
    }

    public KafkaCluster buildKafkaClusterInfo() {
        KafkaCluster kafkaCluster = new KafkaCluster();

        kafkaCluster.setBrokers(getAllBrokersInCluster());
        kafkaCluster.setTopics(getTopics());
        kafkaCluster.setController(getController());

        return kafkaCluster;
    }

    private KafkaBroker getController() {
        return null;
    }

    private List<KafkaBroker> getAllBrokersInCluster() {
        val brokers = this.kafkaClient.getAllBrokersInCluster();
        brokers.sort(Comparator.comparing(KafkaBroker::getId));
        return brokers;
    }

    private List<KafkaTopic> getTopics() {
        val topics = this.kafkaClient.getTopics();
        topics.sort(Comparator.comparing(KafkaTopic::getName));
        return topics;
    }

}
