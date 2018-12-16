package eu.msdhn.kafkamonitor.client;

import eu.msdhn.kafkamonitor.domain.KafkaTopic;

public class KafkaClusterBuilder {

  private String zookeeper;

  private KafkaClusterBuilder(String zookeeper) {
    this.zookeeper = zookeeper;
  }

  public static KafkaClusterBuilder instance(String zookeeper) {
    return new KafkaClusterBuilder(zookeeper);
  }

  public void createTopic(KafkaTopic kafkaTopic) {
    final KafkaAdminClient kafkaAdminClient = new KafkaAdminClient(this.zookeeper);
    try {
      kafkaAdminClient.createTopic(kafkaTopic, RackAwareMode.ENFORCED);

    } finally {
      kafkaAdminClient.close();
    }
  }


}
