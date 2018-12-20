package eu.msdhn.kafkamonitor.config;


import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("kafka.metric")
@Profile("metric")
public class KafkaReportableMetricPropertiesConfig extends KafkaClusterPropertiesConfig {

  @Getter
  @Setter
  private List<BrokerJmxPropertiesUrls> jmxUrls;

  @Getter
  @Setter
  private List<KafkaMBeanPropertiesConfig> brokerMetrics;
}


