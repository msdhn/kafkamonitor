package eu.msdhn.kafkamonitor.config;

import lombok.Getter;
import lombok.Setter;

public class BrokerJmxUrls {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String jmxUrl;
}