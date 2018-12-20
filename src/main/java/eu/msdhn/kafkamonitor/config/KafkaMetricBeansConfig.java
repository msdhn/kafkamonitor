package eu.msdhn.kafkamonitor.config;

import eu.msdhn.kafkamonitor.domain.KafkaMetricType;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricCollectorService;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBrokerMetricCollectorService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBaseMetricReporterService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaDefaultMetricReporterService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"metricdefault"})
public class KafkaMetricBeansConfig {

  @Bean(name = "defaultBrokerMetricReporterService")
  @Autowired
  public KafkaBaseMetricReporterService getDefaultMetricReporter(
      KafkaReportableMetricPropertiesConfig kafkaBrokerMetricConfig) {
    Map<KafkaMetricType, kafkaBaseMetricCollectorService> collectorMap = new HashMap<>();
    collectorMap.put(KafkaMetricType.BROKER,
        new kafkaBrokerMetricCollectorService(kafkaBrokerMetricConfig));
    return
        new KafkaDefaultMetricReporterService(collectorMap, kafkaBrokerMetricConfig);
  }
}
