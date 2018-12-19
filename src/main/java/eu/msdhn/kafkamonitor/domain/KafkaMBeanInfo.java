package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;

import java.util.List;

public class KafkaMBeanInfo {

    @Getter
    private String mbeanName;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private List<KafkaMBeanAttributeValue> attributeValueList;

    public KafkaMBeanInfo() {
    }

    public KafkaMBeanInfo(String mbeanName, String name, List<KafkaMBeanAttributeValue> attributeValueList) {
        this.mbeanName = mbeanName;
        this.attributeValueList = attributeValueList;
        this.name = name;
    }
}
