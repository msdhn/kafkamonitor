package eu.msdhn.kafkamonitor.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("kafka.brokermetric")
public class KafkaBrokerMetricConfig extends KafkaMetricConfig{

    @Getter
    @Setter
    private List<BrokerJmxUrls> jmxUrls;

}


