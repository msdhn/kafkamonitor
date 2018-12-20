package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.config.KafkaReportableMetricPropertiesConfig;
import eu.msdhn.kafkamonitor.domain.KafkaBrokerMetric;
import eu.msdhn.kafkamonitor.metricservice.KafkaJmxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class kafkaBrokerMetricCollectorService extends kafkaBaseMetricCollectorService {

  private static final Logger LOG = LoggerFactory
      .getLogger(kafkaBrokerMetricCollectorService.class);

  private KafkaReportableMetricPropertiesConfig config;

  public kafkaBrokerMetricCollectorService(KafkaReportableMetricPropertiesConfig config) {
    this.config = config;
  }

  @Override
  public KafkaBrokerMetric collectMetric(String jmxUrl) {
    return KafkaJmxUtil.instance().retrieveMetric(jmxUrl, this.config.getBrokerMetrics());
  }

}
