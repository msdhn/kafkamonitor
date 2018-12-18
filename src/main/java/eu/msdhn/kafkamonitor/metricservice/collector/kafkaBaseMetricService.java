package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.domain.KafkaMetric;

public abstract class kafkaBaseMetricService {

    public abstract KafkaMetric collectMetric(String jmxUrl);

}
