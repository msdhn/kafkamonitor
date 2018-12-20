package eu.msdhn.kafkamonitor.metricservice;

import eu.msdhn.kafkamonitor.domain.KafkaMetric;
import java.util.Map;

public interface KafkaMetricDBService {

  void writeBrokerMetrics(Map<Integer, KafkaMetric> metrics);

}
