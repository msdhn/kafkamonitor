package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class kafkaTopicPartition extends AbstractKafkaDetails {

    @Getter
    @Setter
    private String partitionNumber;

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
}
