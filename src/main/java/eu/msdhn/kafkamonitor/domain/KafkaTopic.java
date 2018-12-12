package eu.msdhn.kafkamonitor.domain;

public class KafkaTopic extends AbstractKafkaDetails {

    private String name;

    private Integer partitions;

    private Integer replicationFactor;
}
