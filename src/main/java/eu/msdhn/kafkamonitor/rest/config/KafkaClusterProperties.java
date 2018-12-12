package eu.msdhn.kafkamonitor.rest.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties
public class KafkaClusterProperties {

    @Value("${zookeeper}")
    @Getter
    private List<String> zookeeperHost;



}
