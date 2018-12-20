package eu.msdhn.kafkamonitor.domain;

public class InfluxDBException extends RuntimeException {


  public InfluxDBException(Exception e) {
    super(e);
  }

  public InfluxDBException(String msg) {
    super(msg);
  }
}
