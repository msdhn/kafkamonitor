package eu.msdhn.kafkamonitor.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@ConfigurationProperties("kafka.metric")
@Profile("metric")
public class KafkaReportableMetricConfig extends KafkaClusterAppPropertiesConfig {

    @Getter
    @Setter
    private String influxdbHost;

    @Getter
    @Setter
    private String influxdbUser;

    @Getter
    @Setter
    private String influxdbPassword;


    @Getter
    @Setter
    private List<BrokerJmxUrls> jmxUrls;

    @Getter
    @Setter
    private List<KafkaMBeanConfig> brokerMetrics;
}


