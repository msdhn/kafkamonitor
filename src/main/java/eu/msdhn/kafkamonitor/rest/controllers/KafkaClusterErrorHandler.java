package eu.msdhn.kafkamonitor.rest.controllers;

import eu.msdhn.kafkamonitor.domain.KafkaClusterException;
import eu.msdhn.kafkamonitor.domain.KafkaHttpErrorResponse;
import eu.msdhn.kafkamonitor.domain.KafkaMetricException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class KafkaClusterErrorHandler {

    @ExceptionHandler(value = {KafkaMetricException.class, KafkaClusterException.class})
    public ResponseEntity<KafkaHttpErrorResponse> handleKafkaMetricException(RuntimeException runtimeException) {
        return new ResponseEntity<>(new KafkaHttpErrorResponse(runtimeException), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
