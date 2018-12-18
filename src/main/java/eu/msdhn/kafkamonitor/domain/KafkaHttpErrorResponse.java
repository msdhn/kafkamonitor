package eu.msdhn.kafkamonitor.domain;

import lombok.Getter;

import java.util.Date;

public class KafkaHttpErrorResponse {

    @Getter
    private Date timeStamp;

    @Getter
    private String message;

    @Getter
    private String localizedMessage;

    public KafkaHttpErrorResponse(String message) {
        this.message = message;
        this.timeStamp = new Date();
    }

    public KafkaHttpErrorResponse(RuntimeException runtimeException) {
        this.message = runtimeException.getMessage();
        this.localizedMessage = runtimeException.getLocalizedMessage();
        this.timeStamp = new Date();
    }
}
