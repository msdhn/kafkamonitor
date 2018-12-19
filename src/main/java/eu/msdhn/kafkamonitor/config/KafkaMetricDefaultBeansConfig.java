package eu.msdhn.kafkamonitor.config;

import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBrokerMetricService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBaseMetricReporterService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBrokerDefaultMetricReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"metricdefault"})
public class KafkaMetricDefaultBeansConfig {

    @Autowired
    private KafkaReportableMetricConfig kafkaBrokerMetricConfig;

    @Bean(name = "defaultBrokerMetricReporterService")
    public KafkaBaseMetricReporterService getDefaultMetricReporter() {
        kafkaBaseMetricService kafkaBaseMetricService = new kafkaBrokerMetricService(kafkaBrokerMetricConfig);
        KafkaBrokerDefaultMetricReporterService kafkaDefaultMetricReporterService = new KafkaBrokerDefaultMetricReporterService(kafkaBaseMetricService, kafkaBrokerMetricConfig);
        return kafkaDefaultMetricReporterService;
    }

}
