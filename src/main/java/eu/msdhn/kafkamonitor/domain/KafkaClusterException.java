package eu.msdhn.kafkamonitor.domain;

public class KafkaClusterException extends RuntimeException {

  public KafkaClusterException() {
  }

  public KafkaClusterException(Exception ex) {
    super(ex);
  }

  public KafkaClusterException(String msg) {
    super(msg);
  }
}
