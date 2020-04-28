package jeventbus.kafka.consumer;

import jeventbus.kafka.KafkaEventDeserializer;
import jeventbus.kafka.KafkaEventSerializer;
import jeventbus.kafka.producer.KafkaEventProducer;
import jeventbus.streaming.EventMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static java.time.Duration.ofMillis;

public class KafkaEventConsumer {

    private static final Long POLLING_DELAY = 100L;

    public void consumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "event-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaEventDeserializer.class.getName());

        KafkaConsumer<String, EventMessage> consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList("user-tracking"));
        while(true) {
            ConsumerRecords<String, EventMessage> records = consumer.poll(ofMillis(POLLING_DELAY));
            for (ConsumerRecord<String, EventMessage> record : records) {
                System.out.println(record.value());
            }
        }
    }

    public static void main(String[] args) {
        KafkaEventConsumer consumer = new KafkaEventConsumer();
        consumer.consumer();
    }
}
