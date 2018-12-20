package eu.msdhn.kafkamonitor.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class KafkaMBeanPropertiesConfig {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private List<String> attributes;
}
