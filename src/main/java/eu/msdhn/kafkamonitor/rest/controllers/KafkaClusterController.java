package eu.msdhn.kafkamonitor.rest.controllers;

import eu.msdhn.kafkamonitor.client.KafkaClusterInfoBuilder;
import eu.msdhn.kafkamonitor.domain.KafkaCluster;
import eu.msdhn.kafkamonitor.domain.KafkaTopic;
import eu.msdhn.kafkamonitor.domain.KafkaTopicPartition;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kafka/cluster")
public class KafkaClusterController {

    @RequestMapping(value = "/{zookeeper}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KafkaCluster getKafkaCluster(@PathVariable("zookeeper") String zooKeeper) {
        return KafkaClusterInfoBuilder.instance(zooKeeper).buildKafkaClusterInfo();
    }

    @GetMapping(value = "/{zookeeper}/topic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, List<KafkaTopic>> getTopics(@PathVariable("zookeeper") String zooKeeper) {
        val topics = new HashMap<String, List<KafkaTopic>>();
        topics.put("topics", KafkaClusterInfoBuilder.instance(zooKeeper).buildKafkaClusterInfo().getTopics());
        return topics;
    }

    @GetMapping(value = "/{zookeeper}/partition", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, List<KafkaTopicPartition>> getAllPartitions(@PathVariable("zookeeper") String zooKeeper) {
        val partitions = new HashMap<String, List<KafkaTopicPartition>>();
        partitions.put("partitions", KafkaClusterInfoBuilder.instance(zooKeeper).getAllPartitions());
        return partitions;
    }

    @GetMapping(value = "/{zookeeper}/partition/under-replicated", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, List<KafkaTopicPartition>> getUnderReplicatedPartitions(@PathVariable("zookeeper") String zooKeeper) {
        val resultMap = new HashMap<String, List<KafkaTopicPartition>>();
        resultMap.put("partitions", KafkaClusterInfoBuilder.instance(zooKeeper).getAllPartitions()
                .stream()
                .filter(p -> p.getReplicas().size() > p.getInSyncReplicas().size())
                .collect(Collectors.toList()));
        return resultMap;
    }


}
