package eu.msdhn.kafkamonitor.metricbatch;

import eu.msdhn.kafkamonitor.config.KafkaBrokerMetricConfig;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaDefaultMetricReporterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public final class KafkaBrokerMetricReporter {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaBrokerMetricReporter.class);

    private KafkaDefaultMetricReporterService defaultBrokerMetricReporter;

    @Autowired
    public KafkaBrokerMetricReporter(@Qualifier("defaultBrokerMetricReporter") KafkaDefaultMetricReporterService defaultBrokerMetricReporter,
                                     KafkaBrokerMetricConfig kafkaBrokerMetricConfig) {
        this.defaultBrokerMetricReporter = defaultBrokerMetricReporter;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void report() {
        try {
            this.defaultBrokerMetricReporter.sendMetric();
        } finally {
        }
    }
}
