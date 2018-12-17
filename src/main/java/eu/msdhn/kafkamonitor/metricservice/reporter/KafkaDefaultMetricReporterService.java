package eu.msdhn.kafkamonitor.metricservice.reporter;

public final class KafkaDefaultMetricReporterService extends KafkaBaseMetricReporterService {

    public KafkaDefaultMetricReporterService(eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService kafkaBaseMetricService) {
        super(kafkaBaseMetricService);
    }

    @Override
    public void sendMetric() {
        getKafkaBaseMetricService().collectMetric();
    }
}
