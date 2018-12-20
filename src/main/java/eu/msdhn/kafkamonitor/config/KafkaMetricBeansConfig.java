package eu.msdhn.kafkamonitor.config;

import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBrokerMetricService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBaseMetricReporterService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBrokerDefaultMetricReporterService;
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
    kafkaBrokerMetricService kafkaBrokerMetricService = new kafkaBrokerMetricService(
        kafkaBrokerMetricConfig);
    KafkaBrokerDefaultMetricReporterService kafkaDefaultMetricReporterService = new KafkaBrokerDefaultMetricReporterService(
        kafkaBrokerMetricService, kafkaBrokerMetricConfig);
    return kafkaDefaultMetricReporterService;
  }

}
