package eu.msdhn.kafkamonitor.client;

import kafka.zk.AdminZkClient;

public class KafkaAdminClient extends KafkaClient {

    private AdminZkClient adminZkClient;

    public KafkaAdminClient(String zookeeper) {
        super(zookeeper);
    }
}
