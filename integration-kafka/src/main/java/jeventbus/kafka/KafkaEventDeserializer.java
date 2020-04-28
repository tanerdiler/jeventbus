package jeventbus.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import jeventbus.shared.EventType;
import jeventbus.streaming.*;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class KafkaEventDeserializer implements Deserializer<EventMessage> {

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
        }

        @Override
        public EventMessage deserialize(String topic, byte[] data) {
            PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
                    .builder()
                    .allowIfBaseType("jeventbus")
                    .allowIfBaseType("java.lang")
                    .allowIfBaseType("java.util")
                    .build();
            ObjectMapper mapper = new ObjectMapper();
            mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
            EventMessage object = null;
            try {
                object = mapper.readValue(data, EventMessage.class);
            } catch (Exception exception) {
                System.out.println("Error in deserializing bytes "+ exception);
            }
            return object;
        }

        @Override
        public void close() {
        }
    }