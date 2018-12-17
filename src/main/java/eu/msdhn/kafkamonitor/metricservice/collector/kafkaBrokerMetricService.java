package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.config.KafkaBrokerMetricConfig;
import eu.msdhn.kafkamonitor.metricservice.KafkaMetric;
import lombok.val;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class kafkaBrokerMetricService extends kafkaBaseMetricService {

    private KafkaBrokerMetricConfig config;

    public kafkaBrokerMetricService(KafkaBrokerMetricConfig config) {
        this.config = config;
    }

    @Override
    public KafkaMetric collectMetric() {
        try {
            val mbsConnection = connect("");
            if (mbsConnection != null) {



            }

        } finally {

        }
        return null;
    }


    private MBeanServerConnection connect(String jmxServiceUrl) {

        boolean connected = false;
        JMXConnector jmxConnector = null;
        val connectTestStarted = System.currentTimeMillis();
        val connectTimeoutMs = 10000;
        do {
            try {
                val url = new JMXServiceURL(jmxServiceUrl);
                jmxConnector = JMXConnectorFactory.connect(url, null);
                MBeanServerConnection mbsConnection = jmxConnector.getMBeanServerConnection();
                return mbsConnection;
            } catch (Exception exc) {
                //TODO log
            }
        } while (System.currentTimeMillis() - connectTestStarted < connectTimeoutMs);
        return null;
    }


}
