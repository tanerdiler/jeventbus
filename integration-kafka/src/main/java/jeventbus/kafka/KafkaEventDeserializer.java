package jeventbus.kafka;

import jeventbus.serialization.JsonDeserializer;
import jeventbus.streaming.*;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class KafkaEventDeserializer implements Deserializer<EventMessage> {

    private final JsonDeserializer deserializer;

    public KafkaEventDeserializer() {
        this.deserializer = new JsonDeserializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public EventMessage deserialize(String topic, byte[] data) {
        return deserializer.deserialize(data);
    }

    @Override
    public void close() {
    }
}