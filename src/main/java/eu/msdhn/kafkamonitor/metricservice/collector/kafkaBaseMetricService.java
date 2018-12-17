package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.metricservice.KafkaMetric;

public abstract class kafkaBaseMetricService {

    public abstract KafkaMetric collectMetric();

}
