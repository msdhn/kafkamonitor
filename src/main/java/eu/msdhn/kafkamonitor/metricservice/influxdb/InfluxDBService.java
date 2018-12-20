package eu.msdhn.kafkamonitor.metricservice.influxdb;

import eu.msdhn.kafkamonitor.domain.KafkaMetric;
import java.util.Map;

public interface InfluxDBService {

  void writeBrokerMetrics(Map<Integer, KafkaMetric> metrics);

}
