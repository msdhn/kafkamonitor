package eu.msdhn.kafkamonitor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka")
public class KafkaMetricConfig {

    @Getter
    @Setter
    private String zookeeper;
}
