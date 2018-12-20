package eu.msdhn.kafkamonitor.metricservice.reporter;

import eu.msdhn.kafkamonitor.config.BrokerJmxPropertiesUrls;
import eu.msdhn.kafkamonitor.config.KafkaReportableMetricPropertiesConfig;
import eu.msdhn.kafkamonitor.domain.KafkaMetric;
import eu.msdhn.kafkamonitor.domain.KafkaMetricException;
import eu.msdhn.kafkamonitor.metricservice.influxdb.InfluxDBService;
import java.util.HashMap;
import java.util.Map;
import lombok.val;
import org.influxdb.InfluxDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

@Profile({"metric", "metricinfluxdb"})
public final class KafkaBrokerInfluxDBMetricReporterService extends KafkaBaseMetricReporterService {

  private static final Logger LOG = LoggerFactory
      .getLogger(KafkaBrokerInfluxDBMetricReporterService.class);

  private KafkaReportableMetricPropertiesConfig reportableConfig;

  private InfluxDBService influxDBService;

  public KafkaBrokerInfluxDBMetricReporterService(
      eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService kafkaBaseMetricService,
      KafkaReportableMetricPropertiesConfig reportableConfig,
      InfluxDBService influxDBService) {
    super(kafkaBaseMetricService);
    this.reportableConfig = reportableConfig;
    this.influxDBService = influxDBService;
  }

  @Override
  public void sendMetric() {
    Map<Integer, KafkaMetric> metrics = new HashMap<>();
    for (BrokerJmxPropertiesUrls brokerJmxUrls : this.reportableConfig.getJmxUrls()) {
      try {
        val metric = this.kafkaBaseMetricService.collectMetric(brokerJmxUrls.getJmxUrl());
        metrics.put(brokerJmxUrls.getId(), metric);
      } catch (KafkaMetricException e) {
        //TODO just log and and continue
      }
    }

    try {
      this.influxDBService.writeBrokerMetrics(metrics);
    } catch (InfluxDBException e) {
      //TODO : need to check what to do
    }

  }
}
