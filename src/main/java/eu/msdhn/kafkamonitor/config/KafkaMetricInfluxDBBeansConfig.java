package eu.msdhn.kafkamonitor.config;

import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBrokerMetricService;
import eu.msdhn.kafkamonitor.metricservice.influxdb.InfluxDBService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBaseMetricReporterService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBrokerInfluxDBMetricReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"metricinfluxdb"})
@Configuration
public class KafkaMetricInfluxDBBeansConfig {

  @Bean(name = "influxDbBrokerMetricReporterService")
  @Autowired
  public KafkaBaseMetricReporterService getMetricReporter(
      KafkaReportableMetricPropertiesConfig kafkaBrokerMetricConfig,
      InfluxDBService influxDBService) {
    kafkaBaseMetricService kafkaBaseMetricService = new kafkaBrokerMetricService(
        kafkaBrokerMetricConfig);
    KafkaBaseMetricReporterService kafkaDefaultMetricReporterService = new KafkaBrokerInfluxDBMetricReporterService(
        kafkaBaseMetricService, kafkaBrokerMetricConfig, influxDBService);
    return kafkaDefaultMetricReporterService;
  }
}
