package eu.msdhn.kafkamonitor.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class KafkaCluster extends AbstractKafkaDetails {

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<KafkaBroker> brokers;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<KafkaTopic> topics;

    @Setter
    @Getter
    private KafkaBroker controller;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<KafkaTopic> internalTopics;
}
