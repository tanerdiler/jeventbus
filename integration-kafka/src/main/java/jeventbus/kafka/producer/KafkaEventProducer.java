package jeventbus.kafka.producer;

import jeventbus.kafka.KafkaEventSerializer;
import jeventbus.streaming.EventMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;

public class KafkaEventProducer {

    private Producer<String, EventMessage> producer;

    public KafkaEventProducer connect() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "CLIENT-NAME-1");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaEventSerializer.class.getName());
        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, KafkaEventSerializer.class.getName());

        producer = new KafkaProducer(props);
        return this;
    }

    public void produce(EventMessage eventMessage) {

        ProducerRecord<String, EventMessage>  record = new ProducerRecord("user-tracking", eventMessage.getTraceId(), eventMessage);
        producer.send(record);
    }
}
