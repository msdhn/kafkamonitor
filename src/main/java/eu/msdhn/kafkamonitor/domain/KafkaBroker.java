package eu.msdhn.kafkamonitor.domain;

import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class KafkaBroker extends AbstractKafkaDetails {

  @Getter
  private String id;

  @Getter
  private String host;

  @Getter
  private String rack;

  @Getter
  @Setter
  private Set<KafkaEndPoint> endPoints;

  @Getter
  @Setter
  private List<kafkaTopicPartition> topicPartitions;

  public KafkaBroker(String id, String host, String rack) {
    this.id = id;
    this.host = host;
    this.rack = rack;
  }

  public KafkaBroker(String id) {
    this.id = id;
  }


}
