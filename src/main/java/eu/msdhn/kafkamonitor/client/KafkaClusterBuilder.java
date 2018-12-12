package eu.msdhn.kafkamonitor.client;

import eu.msdhn.kafkamonitor.domain.KafkaBroker;
import eu.msdhn.kafkamonitor.domain.KafkaCluster;
import eu.msdhn.kafkamonitor.domain.KafkaTopic;
import lombok.val;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class KafkaClusterBuilder {

    private KafkaClient kafkaClient;

    private KafkaClusterBuilder(String zooKeeper) {
        this.kafkaClient = new KafkaClient(zooKeeper);
    }

    public static KafkaClusterBuilder instance(String zooKeeper) {
        return new KafkaClusterBuilder(zooKeeper);
    }

    public KafkaCluster buildKafkaCluster() {
        KafkaCluster kafkaCluster = new KafkaCluster();

        kafkaCluster.setBrokers(getAllBrokersInCluster());
        kafkaCluster.setTopics(getTopics());
        kafkaCluster.setInternalTopics(getInternalTopics());
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
        return Arrays.asList();
    }


    private List<KafkaTopic> getInternalTopics() {
        return Arrays.asList();
    }

}
