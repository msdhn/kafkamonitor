package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.domain.KafkaMetric;

public abstract class kafkaBaseMetricCollectorService {

    public abstract KafkaMetric collectMetric(String jmxUrl);

}
