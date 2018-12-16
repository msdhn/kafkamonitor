package eu.msdhn.kafkamonitor.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class kafkaTopicPartition extends AbstractKafkaDetails {

  @Getter
  @Setter
  private String partitionNumber;

  @Getter
  @Setter
  private KafkaTopic topic;

  @Setter
  @Getter
  private KafkaBroker leader;

  @Setter
  @Getter
  private List<KafkaBroker> replicas;

  @Setter
  @Getter
  private List<KafkaBroker> inSyncReplicas;
}
