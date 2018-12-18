package eu.msdhn.kafkamonitor.metricbatch;

import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBaseMetricReporterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Profile("metric")
public final class KafkaBrokerMetricReporter {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaBrokerMetricReporter.class);

    private KafkaBaseMetricReporterService defaultBrokerMetricReporterService;

    @Autowired
    public KafkaBrokerMetricReporter(@Qualifier("defaultBrokerMetricReporterService") KafkaBaseMetricReporterService defaultBrokerMetricReporterService) {
        this.defaultBrokerMetricReporterService = defaultBrokerMetricReporterService;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void report() {
        this.defaultBrokerMetricReporterService.sendMetric();
    }
}
