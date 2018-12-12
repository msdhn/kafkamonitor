package eu.msdhn.kafkamonitor.client;

import eu.msdhn.kafkamonitor.domain.KafkaBroker;
import eu.msdhn.kafkamonitor.domain.KafkaEndPoint;
import kafka.zk.KafkaZkClient;
import lombok.val;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.utils.Time;
import scala.Int;
import scala.collection.JavaConverters;

import java.util.List;
import java.util.stream.Collectors;

public class KafkaClient {

    private KafkaZkClient zkClient;

    public KafkaClient(String zooKeeperString) {
        zkClient = KafkaZkClient.apply("localhost:2181", JaasUtils.isZkSecurityEnabled(), 30000, 30000, Int.MaxValue(), Time.SYSTEM, "", "");
    }

    public List<KafkaBroker> getAllBrokersInCluster() {
        val brokerList = this.zkClient.getAllBrokersInCluster();
        return JavaConverters.asJavaCollection(brokerList).stream().map(b -> {
            String rack = b.rack().isDefined() ? b.rack().get() : "undefined";
            val kafkaBroker = new KafkaBroker(b.id(), "", rack);
            kafkaBroker.setEndPoints(
                    JavaConverters.asJavaCollection(b.endPoints()).stream().map(
                            p -> {
                                val kafkaEndPoint = new KafkaEndPoint();
                                kafkaEndPoint.setHost(p.host());
                                kafkaEndPoint.setPort(p.port());
                                return kafkaEndPoint;
                            }
                    ).collect(Collectors.toSet())
            );
            return kafkaBroker;
        }).collect(Collectors.toList());
    }

}

