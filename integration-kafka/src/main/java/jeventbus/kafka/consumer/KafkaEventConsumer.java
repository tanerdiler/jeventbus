package jeventbus.kafka.consumer;

import jeventbus.core.Events;
import jeventbus.kafka.KafkaEventDeserializer;
import jeventbus.shared.EventSource;
import jeventbus.streaming.EventMessage;
import jeventbus.streaming.EventToMessageConverter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.util.Arrays;
import java.util.Properties;

import static java.time.Duration.ofMillis;
import static java.util.Objects.nonNull;

public class KafkaEventConsumer {

    private static final Long POLLING_DELAY = 100L;
    private final String host;
    private String groupId;

    public KafkaEventConsumer(String host, String groupId) {
        this.host = host;
        this.groupId = groupId;
    }

    public void consume(String... topics) {
        Properties props = new Properties();
        props.put("bootstrap.servers", host);
        props.put("group.id", groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaEventDeserializer.class.getName());

        KafkaConsumer<String, EventMessage> consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList(topics));
        while(true) {
            ConsumerRecords<String, EventMessage> records = consumer.poll(ofMillis(POLLING_DELAY));
            for (ConsumerRecord<String, EventMessage> record : records) {
                EventMessage eventMessage = record.value();
                if (nonNull(eventMessage)) {
                    EventSource source = EventToMessageConverter.convert(eventMessage);
                    Events.event(eventMessage.getEvent()).fire(source);
                }
            }
        }
    }
}
