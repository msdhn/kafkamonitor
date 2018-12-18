package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.config.KafkaBrokerMetricConfig;
import eu.msdhn.kafkamonitor.domain.KafkaBrokerMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class kafkaBrokerMetricService extends kafkaBaseMetricService {

    private static final Logger LOG = LoggerFactory.getLogger(kafkaBrokerMetricService.class);

    private KafkaBrokerMetricConfig config;

    public kafkaBrokerMetricService(KafkaBrokerMetricConfig config) {
        this.config = config;
    }

    @Override
    public KafkaBrokerMetric collectMetric(String jmxUrl) {
        return KafkaJmxUtil.instance().retrieveMetric(jmxUrl, this.config.getMetrics());
    }

}
