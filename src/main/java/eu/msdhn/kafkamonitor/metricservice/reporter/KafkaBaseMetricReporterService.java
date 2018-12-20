package eu.msdhn.kafkamonitor.metricservice.reporter;

import eu.msdhn.kafkamonitor.domain.KafkaMetricType;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricCollectorService;
import java.util.Map;
import lombok.Getter;

public abstract class KafkaBaseMetricReporterService {

  @Getter
  protected Map<KafkaMetricType, kafkaBaseMetricCollectorService> kafkaBaseMetricServiceMap;

  protected KafkaBaseMetricReporterService(
      Map<KafkaMetricType, kafkaBaseMetricCollectorService> kafkaBaseMetricServiceMap) {
    this.kafkaBaseMetricServiceMap = kafkaBaseMetricServiceMap;
  }

  public abstract void sendMetric();
}
