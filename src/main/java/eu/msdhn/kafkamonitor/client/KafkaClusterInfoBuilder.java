package eu.msdhn.kafkamonitor.client;

import eu.msdhn.kafkamonitor.domain.KafkaBroker;
import eu.msdhn.kafkamonitor.domain.KafkaCluster;
import eu.msdhn.kafkamonitor.domain.KafkaTopic;
import lombok.val;

import java.util.Comparator;
import java.util.List;

public class KafkaClusterInfoBuilder {

    private String zookeeper;

    private KafkaClusterInfoBuilder(String zooKeeper) {
        this.zookeeper = zooKeeper;
    }

    public static KafkaClusterInfoBuilder instance(String zooKeeper) {
        return new KafkaClusterInfoBuilder(zooKeeper);
    }

    public KafkaCluster buildKafkaClusterInfo() {
        KafkaClient kafkaClient = new KafkaClient(this.zookeeper);
        try {
            KafkaCluster kafkaCluster = new KafkaCluster();

            kafkaCluster.setBrokers(getAllBrokersInCluster(kafkaClient));
            kafkaCluster.setTopics(getTopics(kafkaClient));
            kafkaCluster.setController(getController());

            return kafkaCluster;
        } finally {
            kafkaClient.close();
        }
    }

    private KafkaBroker getController() {
        return null;
    }

    private List<KafkaBroker> getAllBrokersInCluster(KafkaClient kafkaClient) {
        val brokers = kafkaClient.getAllBrokersInCluster();
        brokers.sort(Comparator.comparing(KafkaBroker::getId));
        return brokers;
    }

    private List<KafkaTopic> getTopics(KafkaClient kafkaClient) {
        val topics = kafkaClient.getTopics();
        topics.sort(Comparator.comparing(KafkaTopic::getName));
        return topics;
    }

}
