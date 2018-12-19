package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.config.KafkaReportableMetricConfig;
import eu.msdhn.kafkamonitor.domain.KafkaBrokerMetric;
import eu.msdhn.kafkamonitor.metricservice.KafkaJmxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class kafkaBrokerMetricService extends kafkaBaseMetricService {

    private static final Logger LOG = LoggerFactory.getLogger(kafkaBrokerMetricService.class);

    private KafkaReportableMetricConfig config;

    public kafkaBrokerMetricService(KafkaReportableMetricConfig config) {
        this.config = config;
    }

    @Override
    public KafkaBrokerMetric collectMetric(String jmxUrl) {
        return KafkaJmxUtil.instance().retrieveMetric(jmxUrl, this.config.getBrokerMetrics());
    }

}
