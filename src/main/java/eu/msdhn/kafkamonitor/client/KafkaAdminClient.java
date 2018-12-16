package eu.msdhn.kafkamonitor.client;

import eu.msdhn.kafkamonitor.domain.KafkaClusterBadRequestException;
import eu.msdhn.kafkamonitor.domain.KafkaClusterException;
import eu.msdhn.kafkamonitor.domain.KafkaTopic;
import kafka.zk.AdminZkClient;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.TopicExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class KafkaAdminClient extends KafkaClient {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaAdminClient.class);

    private AdminZkClient adminZkClient;

    public KafkaAdminClient(String zookeeper) {
        super(zookeeper);
    }

    public void createTopic(KafkaTopic kafkaTopic, RackAwareMode rackAwareMode) {
        try {
            this.adminZkClient.createTopic(kafkaTopic.getName(),
                    kafkaTopic.getPartitions(),
                    kafkaTopic.getReplicationFactor(),
                    kafkaTopic.getConfig(),
                    rackAwareMode.mode());

        } catch (TopicExistsException e) {
            LOG.error(String.format("topic names %s is already present in cluster %s", kafkaTopic.getName(), this.getZookeeper()));
            throw new KafkaClusterBadRequestException();
        } catch (KafkaException e) {
            LOG.error("unexpected exception from kafka cluster", e);
            throw new KafkaClusterException(e);
        }
    }

}
