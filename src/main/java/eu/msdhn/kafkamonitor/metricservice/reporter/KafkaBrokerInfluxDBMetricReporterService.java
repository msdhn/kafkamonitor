package eu.msdhn.kafkamonitor.metricservice.reporter;

import eu.msdhn.kafkamonitor.config.BrokerJmxUrls;
import eu.msdhn.kafkamonitor.config.KafkaReportableMetricConfig;
import eu.msdhn.kafkamonitor.domain.KafkaMetricException;
import lombok.val;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Profile({"metric", "metricinfluxdb"})
public final class KafkaBrokerInfluxDBMetricReporterService extends KafkaBaseMetricReporterService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaBrokerInfluxDBMetricReporterService.class);

    private KafkaReportableMetricConfig config;

    public KafkaBrokerInfluxDBMetricReporterService(eu.msdhn.kafkamonitor.metricservice.collector.kafkaBaseMetricService kafkaBaseMetricService, KafkaReportableMetricConfig config) {
        super(kafkaBaseMetricService);
        this.config = config;
    }

    @Override
    public void sendMetric() {
        val kafkaBaseMetricService = this.getKafkaBaseMetricService();
        InfluxDB influxDB = null;
        try {
            influxDB = InfluxDBFactory.connect(this.config.getInfluxdbHost(), this.config.getInfluxdbUser(), this.config.getInfluxdbPassword());
            Pong response = influxDB.ping();
            if (response.getVersion().equalsIgnoreCase("unknown")) {
                LOG.error("Error pinging influxDB server.");
                return;
            }
            createDB(influxDB);
            influxDB.setDatabase("kafkacluster");
            influxDB.enableBatch();
            val currentTime = System.currentTimeMillis();
            for (BrokerJmxUrls brokerJmxUrls : this.config.getJmxUrls()) {
                val metric = kafkaBaseMetricService.collectMetric(brokerJmxUrls.getJmxUrl());
                val brokerId = brokerJmxUrls.getId();
                for (val eachBean : metric.getMBeans()) {
                    val p = Point.measurement("broker_" + eachBean.getName().toLowerCase());
                    p.time(currentTime, TimeUnit.MILLISECONDS);
                    p.addField("broker", brokerId);
                    eachBean.getAttributeValueList().forEach(attr -> {
                        p.addField(attr.getAttribute(), attr.getValue());
                    });
                    try {
                        influxDB.write(p.build());
                        influxDB.flush();
                    } catch (RuntimeException e) {
                        LOG.error("writing failed to influxdb", e);
                    }
                }
            }
        } catch (KafkaMetricException metricException) {
            LOG.error("error", metricException);
            //TODO
        } catch (RuntimeException e) {
            LOG.error("", e);
        } finally {
            if (influxDB != null) {
                influxDB.close();
            }
        }
    }

    private void createDB(InfluxDB influxDB) {
        try {
            // influxDB.createDatabase("kafkacluster");
            // influxDB.createRetentionPolicy("defaultPolicy", "kafkacluster", "30d", 1, true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            // TODO
        }
    }
}
