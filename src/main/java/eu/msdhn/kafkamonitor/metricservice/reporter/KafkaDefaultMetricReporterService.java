package eu.msdhn.kafkamonitor.metricservice.reporter;

import eu.msdhn.kafkamonitor.config.BrokerJmxUrls;
import eu.msdhn.kafkamonitor.config.KafkaBrokerMetricConfig;
import eu.msdhn.kafkamonitor.domain.KafkaMetricException;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KafkaDefaultMetricReporterService extends KafkaBaseMetricReporterService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaDefaultMetricReporterService.class);

    private KafkaBrokerMetricConfig config;

    public KafkaDefaultMetricReporterService(eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService kafkaBaseMetricService, KafkaBrokerMetricConfig config) {
        super(kafkaBaseMetricService);
        this.config = config;
    }

    @Override
    public void sendMetric() {
        val kafkaBaseMetricService = this.getKafkaBaseMetricService();
        for (BrokerJmxUrls brokerJmxUrls : this.config.getJmxUrls()) {
            try {
                val metric = kafkaBaseMetricService.collectMetric(brokerJmxUrls.getJmxUrl());
                if (metric != null
                        && metric.getMBeans() != null
                        && metric.getMBeans().size() > 0) {
                    System.out.print(String.format("..........................broker id : %s.......................... \n ", brokerJmxUrls.getId()));
                    metric.getMBeans().forEach(mBean -> {
                        System.out.print(String.format("    %s\n", mBean.getMbeanName()));
                        mBean.getAttributeValueList().forEach(attr -> {
                            System.out.print(String.format("        %s : %s \n", attr.getAttribute(), attr.getValue()));
                        });
                    });
                    System.out.print(String.format("................................................................... \n ", brokerJmxUrls.getId()));
                } else {
                    System.out.println(String.format("no metric for broker : %s", brokerJmxUrls.getId()));
                }
            } catch (KafkaMetricException metricException) {
                LOG.error("error", metricException);
                System.out.println(String.format("metric for broker : %s can not be fetched. The reason is %s", brokerJmxUrls.getId(), metricException.getMessage()));
            }
        }
    }
}
