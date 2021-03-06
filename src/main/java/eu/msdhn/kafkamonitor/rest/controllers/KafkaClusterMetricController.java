package eu.msdhn.kafkamonitor.rest.controllers;

import eu.msdhn.kafkamonitor.config.KafkaReportableMetricPropertiesConfig;
import eu.msdhn.kafkamonitor.domain.KafkaMetric;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBrokerMetricCollectorService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka/metric")
@Profile("metric")
public class KafkaClusterMetricController {

  private KafkaReportableMetricPropertiesConfig kafkaBrokerMetricConfig;

  @Autowired
  public KafkaClusterMetricController(
      KafkaReportableMetricPropertiesConfig kafkaBrokerMetricConfig) {
    this.kafkaBrokerMetricConfig = kafkaBrokerMetricConfig;
  }

  @GetMapping(value = "broker", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public KafkaMetric getBrokerMetrics(@RequestParam("jmxurl") String jmxUrl) {
    Objects.requireNonNull(jmxUrl);
    return new kafkaBrokerMetricCollectorService(
        this.kafkaBrokerMetricConfig).collectMetric(jmxUrl);
  }
}
