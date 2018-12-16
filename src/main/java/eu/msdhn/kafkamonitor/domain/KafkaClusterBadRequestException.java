package eu.msdhn.kafkamonitor.domain;

public class KafkaClusterBadRequestException extends KafkaClusterException {

    public KafkaClusterBadRequestException(Exception ex) {
        super(ex);
    }

    public KafkaClusterBadRequestException() {
    }
}
