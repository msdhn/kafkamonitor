package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class KafkaTopicPartition extends AbstractKafkaDetails {

    @Getter
    @Setter
    private int partitionNumber;

    @Getter
    @Setter
    private KafkaTopic topic;

    @Setter
    @Getter
    private KafkaBroker leader;

    @Setter
    @Getter
    private List<KafkaBroker> replicas;

    @Setter
    @Getter
    private List<KafkaBroker> inSyncReplicas;

    public KafkaTopicPartition() {

    }
}
