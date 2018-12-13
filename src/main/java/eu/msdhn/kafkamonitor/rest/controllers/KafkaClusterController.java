package eu.msdhn.kafkamonitor.rest.controllers;

import eu.msdhn.kafkamonitor.client.KafkaClusterInfoBuilder;
import eu.msdhn.kafkamonitor.domain.KafkaCluster;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaClusterController {

    @RequestMapping(value = "cluster", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KafkaCluster getKafkaCluster(@RequestParam("zookeeper") String zooKeeper) {
        return KafkaClusterInfoBuilder.instance(zooKeeper).buildKafkaClusterInfo();
    }

}
