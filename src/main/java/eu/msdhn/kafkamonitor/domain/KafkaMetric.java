package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

abstract public class KafkaMetric extends AbstractKafkaDetails {

    @Getter
    @Setter
    private List<KafkaMBeanInfo> mBeans;
}
