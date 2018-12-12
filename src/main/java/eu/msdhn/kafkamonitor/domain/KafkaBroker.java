package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

public class KafkaBroker extends AbstractKafkaDetails {

    @Getter
    private int id;

    @Getter
    private String host;

    @Getter
    private String rack;

    @Getter
    @Setter
    private Set<KafkaEndPoint> endPoints;

    @Getter
    @Setter
    private List<kafkaTopicPartition> topicPartitions;

    public KafkaBroker(int id, String host, String rack) {
        this.id = id;
        this.host = host;
        this.rack = rack;
    }
}

