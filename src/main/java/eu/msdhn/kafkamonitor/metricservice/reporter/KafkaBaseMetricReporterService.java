package eu.msdhn.kafkamonitor.metricservice.reporter;

import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService;
import lombok.Getter;

public abstract class KafkaBaseMetricReporterService {

  @Getter
  protected kafkaBaseMetricService kafkaBaseMetricService;

  protected KafkaBaseMetricReporterService(kafkaBaseMetricService kafkaBaseMetricService) {
    this.kafkaBaseMetricService = kafkaBaseMetricService;
  }

  public abstract void sendMetric();
}
