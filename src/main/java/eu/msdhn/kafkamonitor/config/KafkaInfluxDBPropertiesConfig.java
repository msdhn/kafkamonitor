package eu.msdhn.kafkamonitor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"metricinfluxdb"})
@Configuration
@ConfigurationProperties("kafka.metric.influxdb")
public class KafkaInfluxDBPropertiesConfig {

  @Getter
  @Setter
  private String host;

  @Getter
  @Setter
  private String user;

  @Getter
  @Setter
  private String password;

  @Getter
  @Setter
  private boolean enableGzip;
}
