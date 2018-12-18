package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.Setter;

public class KafkaBrokerMetric extends KafkaMetric {

    @Getter
    @Setter
    private String jmxUrl;
}
