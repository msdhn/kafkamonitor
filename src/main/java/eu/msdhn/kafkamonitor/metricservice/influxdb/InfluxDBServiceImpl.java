package eu.msdhn.kafkamonitor.metricservice.influxdb;

import eu.msdhn.kafkamonitor.config.InfluxDBConnectionFactory;
import eu.msdhn.kafkamonitor.domain.KafkaMetric;
import eu.msdhn.kafkamonitor.metricservice.KafkaMetricDBService;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.val;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfluxDBServiceImpl implements KafkaMetricDBService {

  private InfluxDBConnectionFactory connectionFactory;

  @Autowired
  public InfluxDBServiceImpl(InfluxDBConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void writeBrokerMetrics(final Map<Integer, KafkaMetric> metrics) {
    val connection = this.connectionFactory.getConnection();

    try {
      BatchPoints batchPoints = BatchPoints
          .database("kafkacluster")
          // .retentionPolicy("defaultPolicy")
          .build();

      int timeIncrement = 0;
      for (val eachBroker : metrics.entrySet()) {
        for (val bean : eachBroker.getValue().getMBeans()) {
          val p = Point.measurement("broker_" + bean.getName().toLowerCase())
              .time(System.currentTimeMillis() + timeIncrement++, TimeUnit.MILLISECONDS)
              .tag("broker", String.valueOf(eachBroker.getKey()));
          bean.getAttributeValueList().forEach(attr -> {
            p.addField(attr.getAttribute().toLowerCase(), attr.getValue());
          });
          p.addField("brokerid", eachBroker.getKey());
          batchPoints.point(p.build());
        }
      }
      connection.write(batchPoints);

    } catch (RuntimeException e) {
      e.printStackTrace();
      //TODO
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
  }


}
