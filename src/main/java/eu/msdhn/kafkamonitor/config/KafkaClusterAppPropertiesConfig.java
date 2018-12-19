package eu.msdhn.kafkamonitor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class KafkaClusterAppPropertiesConfig {

    @Getter
    @Setter
    private String zookeeper;

}
