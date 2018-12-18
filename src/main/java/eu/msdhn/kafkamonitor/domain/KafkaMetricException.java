package eu.msdhn.kafkamonitor.domain;

public class KafkaMetricException extends RuntimeException {

    public KafkaMetricException(Exception e) {
        super(e);
    }

    public KafkaMetricException(String msg) {
        super(msg);
    }

}
