package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

public class KafkaTopic extends AbstractKafkaDetails {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer partitions;

    @Getter
    @Setter
    private Integer replicationFactor;

    @Getter
    @Setter
    private boolean markedForDeletion;

    @Getter
    @Setter
    private Properties config;

    @Getter
    @Setter
    private boolean internal;

    public KafkaTopic(String name) {
        this.name = name;
    }

    public KafkaTopic() {
    }
}
