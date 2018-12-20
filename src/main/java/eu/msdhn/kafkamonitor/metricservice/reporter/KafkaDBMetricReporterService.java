package eu.msdhn.kafkamonitor.metricservice.reporter;

import eu.msdhn.kafkamonitor.config.BrokerJmxPropertiesUrls;
import eu.msdhn.kafkamonitor.config.KafkaReportableMetricPropertiesConfig;
import eu.msdhn.kafkamonitor.domain.KafkaMetric;
import eu.msdhn.kafkamonitor.domain.KafkaMetricException;
import eu.msdhn.kafkamonitor.domain.KafkaMetricType;
import eu.msdhn.kafkamonitor.metricservice.KafkaMetricDBService;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricCollectorService;
import java.util.HashMap;
import java.util.Map;
import lombok.val;
import org.influxdb.InfluxDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

@Profile({"metric", "metricinfluxdb"})
public final class KafkaDBMetricReporterService extends KafkaBaseMetricReporterService {

  private static final Logger LOG = LoggerFactory
      .getLogger(KafkaDBMetricReporterService.class);

  private KafkaReportableMetricPropertiesConfig reportableConfig;
  private KafkaMetricDBService dbService;

  public KafkaDBMetricReporterService(
      Map<KafkaMetricType, kafkaBaseMetricCollectorService> kafkaBaseMetricServices,
      KafkaReportableMetricPropertiesConfig reportableConfig,
      KafkaMetricDBService dbService) {
    super(kafkaBaseMetricServices);
    this.reportableConfig = reportableConfig;
    this.dbService = dbService;
  }

  @Override
  public void sendMetric() {
    sebdBrokerMetric();
  }

  private void sebdBrokerMetric() {
    Map<Integer, KafkaMetric> metrics = new HashMap<>();
    for (BrokerJmxPropertiesUrls brokerJmxUrls : this.reportableConfig.getJmxUrls()) {
      try {
        val metric = this.kafkaBaseMetricServiceMap
            .get(KafkaMetricType.BROKER)
            .collectMetric(brokerJmxUrls.getJmxUrl());
        metrics.put(brokerJmxUrls.getId(), metric);
      } catch (KafkaMetricException e) {
        //TODO just log and and continue
      }
    }
    try {
      this.dbService.writeBrokerMetrics(metrics);
    } catch (InfluxDBException e) {
      //TODO : need to check what to do
    }
  }

}
