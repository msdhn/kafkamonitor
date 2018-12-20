package eu.msdhn.kafkamonitor.config;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConnectionFactory {

  private static final Logger LOG = LoggerFactory.getLogger(InfluxDBConnectionFactory.class);
  private KafkaInfluxDBPropertiesConfig config;

  @Autowired
  public InfluxDBConnectionFactory(KafkaInfluxDBPropertiesConfig config) {
    this.config = config;
  }

  public InfluxDB getConnection() {
    Objects.requireNonNull(this.config.getHost());
    try {
      InfluxDB connection = null;
      final Builder client = new OkHttpClient.Builder()
          .connectTimeout(60, TimeUnit.SECONDS)
          .writeTimeout(60, TimeUnit.SECONDS)
          .readTimeout(60, TimeUnit.SECONDS);
      connection = InfluxDBFactory
          .connect(this.config.getHost(), this.config.getUser(), this.config.getPassword(), client);
      if (this.config.isEnableGzip()) {
        LOG.debug("Enabled gzip compression for HTTP requests");
        connection.enableGzip();
      }
      return connection;
    } catch (RuntimeException e) {
      LOG.error("influxdb connetion error", e);
      throw new InfluxDBException(e.getMessage());
    }
  }
}
