package eu.msdhn.kafkamonitor;

import kafka.zk.KafkaZkClient;
import lombok.val;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.utils.Time;
import scala.Int;
import scala.collection.JavaConverters;

public class Test {

  public static void main(String... args) {

    val time = Time.SYSTEM;
    val zkClient = KafkaZkClient
        .apply("localhost:2181", JaasUtils.isZkSecurityEnabled(), 30000, 30000, Int.MaxValue(),
            time, "", "");

    val x = zkClient.getSortedBrokerList();

    val y = zkClient.getAllBrokersInCluster();

    JavaConverters.asJavaCollection(y).forEach(p -> System.out.print(

        p.endPoints()

    ));


  }

}
