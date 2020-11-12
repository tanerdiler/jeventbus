package jeventbus.kafka;

import jeventbus.serialization.JsonSerializer;
import jeventbus.streaming.*;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaEventSerializer implements Serializer<EventMessage> {
    private JsonSerializer serializer;

    public KafkaEventSerializer() {
        this.serializer = new JsonSerializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, EventMessage data) {
        return serializer.serialize(data);
    }

    @Override
    public void close() {
    }
}