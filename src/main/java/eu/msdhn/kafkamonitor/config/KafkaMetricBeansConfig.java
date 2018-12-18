package eu.msdhn.kafkamonitor.config;

import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService;
import eu.msdhn.kafkamonitor.metricservice.collector.kafkaBrokerMetricService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaBaseMetricReporterService;
import eu.msdhn.kafkamonitor.metricservice.reporter.KafkaDefaultMetricReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("metric")
public class KafkaMetricBeansConfig {

    @Autowired
    private KafkaBrokerMetricConfig kafkaBrokerMetricConfig;

    @Bean(name = "defaultBrokerMetricReporterService")
    public KafkaBaseMetricReporterService getDefaultMetricReporter() {
        kafkaBaseMetricService kafkaBaseMetricService = new kafkaBrokerMetricService(kafkaBrokerMetricConfig);
        KafkaDefaultMetricReporterService kafkaDefaultMetricReporterService = new KafkaDefaultMetricReporterService(kafkaBaseMetricService, kafkaBrokerMetricConfig);
        return kafkaDefaultMetricReporterService;
    }


}
