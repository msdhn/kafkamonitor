package eu.msdhn.kafkamonitor.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

abstract public class KafkaMetric extends AbstractKafkaDetails {

  @Getter
  @Setter
  private List<KafkaMBeanInfo> mBeans;






}
