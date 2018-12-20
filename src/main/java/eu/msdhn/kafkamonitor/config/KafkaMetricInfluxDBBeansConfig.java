package eu.msdhn.kafkamonitor.config;

import eu.msdhn.kafkamonitor.domain.KafkaMetricType;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricCollectorService;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBrokerMetricCollectorService;
import eu.msdhn.kafkamonitor.metricservice.influxdb.InfluxDBServiceImpl;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBaseMetricReporterService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaDBMetricReporterService;
import java.util.HashMap;
import java.util.Map;
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
      InfluxDBServiceImpl influxDBServiceImpl) {

    Map<KafkaMetricType, kafkaBaseMetricCollectorService> collectors = new HashMap<>();
    collectors.put(KafkaMetricType.BROKER, new kafkaBrokerMetricCollectorService(
        kafkaBrokerMetricConfig));

    return new KafkaDBMetricReporterService(
        collectors, kafkaBrokerMetricConfig, influxDBServiceImpl);
  }
}
