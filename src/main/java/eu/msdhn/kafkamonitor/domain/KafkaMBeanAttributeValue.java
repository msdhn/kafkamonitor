package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
public class KafkaMBeanAttributeValue {

    @Getter
    private String attribute;

    @Getter
    private String description;


    @Getter
    private String value;

    public KafkaMBeanAttributeValue(String attribute, String description, String value) {
        this.attribute = attribute;
        this.value = value;
        this.description = description;
    }
}