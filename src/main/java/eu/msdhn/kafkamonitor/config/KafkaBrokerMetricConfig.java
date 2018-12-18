package eu.msdhn.kafkamonitor.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@ConfigurationProperties("kafka.brokermetric")
@Profile("metric")
public class KafkaBrokerMetricConfig extends KafkaClusterPropertiesConfig {

    @Getter
    @Setter
    private List<BrokerJmxUrls> jmxUrls;

    @Getter
    @Setter
    private List<KafkaMBeanConfig> metrics;
}


