package eu.msdhn.kafkamonitor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestProduceConsumeData {


    public static void main(String... args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.submit(new TestProducer("topic1"));
        executorService.submit(new TestProducer("topic2"));
        executorService.submit(new TestProducer("topic3"));

    }


    static class TestProducer implements Runnable {

        private String topicName;

        public TestProducer(String name) {
            this.topicName = name;
        }

        public Producer<String, String> createProducer() {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ProducerConfig.CLIENT_ID_CONFIG, Thread.currentThread().getName());
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringSerializer");
            return new KafkaProducer<>(props);
        }

        @Override
        public void run() {
            Producer<String, String> p = createProducer();
            long count = 1;
            while (true) {
                String key = String.valueOf(count);
                ProducerRecord<String, String> producerRecord = new ProducerRecord(this.topicName, key,
                        this.topicName + "value" + key);
                p.send(producerRecord);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TestConsumer {

        public void consume() {

        }

    }

}


