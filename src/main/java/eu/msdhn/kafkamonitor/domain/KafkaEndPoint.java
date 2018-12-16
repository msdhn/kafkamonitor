package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.Setter;

public class KafkaEndPoint extends AbstractKafkaDetails {

  @Getter
  @Setter
  private String host;

  @Getter
  @Setter
  private int port;
}
