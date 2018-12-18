package eu.msdhn.kafkamonitor.metricservice.collector;

import eu.msdhn.kafkamonitor.config.KafkaMBeanConfig;
import eu.msdhn.kafkamonitor.domain.KafkaBrokerMetric;
import eu.msdhn.kafkamonitor.domain.KafkaMBeanAttributeValue;
import eu.msdhn.kafkamonitor.domain.KafkaMBeanInfo;
import eu.msdhn.kafkamonitor.domain.KafkaMetricException;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final public class KafkaJmxUtil {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaJmxUtil.class);

    private static final int CONN_TIME_OUT = 10000;

    private KafkaJmxUtil() {
    }

    public static KafkaJmxUtil instance() {
        return new KafkaJmxUtil();
    }

    private String getAttributeValue(MBeanServerConnection server, ObjectName objectName, String attribute) {
        try {
            return server.getAttribute(objectName, attribute).toString();
        } catch (IOException
                | ReflectionException
                | InstanceNotFoundException
                | AttributeNotFoundException
                | MBeanException e) {
            LOG.error("unexpected exception", e);
        }
        return "";
    }

    private MBeanServerConnection connect(String jmxServiceUrl) {
        Objects.requireNonNull(jmxServiceUrl, "jmx url is required");
        JMXConnector jmxConnector = null;
        val connectTestStarted = System.currentTimeMillis();
        do {
            try {
                val url = new JMXServiceURL(jmxServiceUrl);
                jmxConnector = JMXConnectorFactory.connect(url, null);
                MBeanServerConnection mbsConnection = jmxConnector.getMBeanServerConnection();
                return mbsConnection;
            } catch (MalformedURLException e) {
                LOG.error("invalid jmx url", e);
                throw new KafkaMetricException(e.getMessage());
            } catch (IOException e) {
                LOG.error(String.format("can not connect to jmx url %s", jmxServiceUrl), e);
                throw new KafkaMetricException(e.getMessage());
            }
        } while (System.currentTimeMillis() - connectTestStarted < CONN_TIME_OUT);
    }

    public KafkaBrokerMetric retrieveMetric(String jmxServiceUrl, List<KafkaMBeanConfig> metrics) {
        Objects.requireNonNull(jmxServiceUrl);
        val mbsConnection = connect(jmxServiceUrl);
        KafkaBrokerMetric kafkaBrokerMetric = new KafkaBrokerMetric();
        try {
            List<KafkaMBeanInfo> kafkaMBeans = new ArrayList<>();
            for (KafkaMBeanConfig metric : metrics) {
                Objects.requireNonNull(metric);
                Objects.requireNonNull(metric.getName());
                for (ObjectName mbeanName : mbsConnection.queryNames(new ObjectName(metric.getName()), null)) {
                    kafkaMBeans.add(new KafkaMBeanInfo(mbeanName.getCanonicalName(),
                            Arrays.stream(mbsConnection.getMBeanInfo(mbeanName).getAttributes()).map(a ->
                                    new KafkaMBeanAttributeValue(a.getName(), a.getDescription(), getAttributeValue(mbsConnection, mbeanName, a.getName()))
                            ).collect(Collectors.toList())));
                }
            }
            kafkaBrokerMetric.setJmxUrl(jmxServiceUrl);
            kafkaBrokerMetric.setMBeans(kafkaMBeans);
        } catch (IOException
                | ReflectionException
                | InstanceNotFoundException
                | IntrospectionException
                | MalformedObjectNameException e) {
            LOG.error("unexpected exception", e);
            throw new KafkaMetricException(e.getMessage());
        }
        return kafkaBrokerMetric;
    }

}
