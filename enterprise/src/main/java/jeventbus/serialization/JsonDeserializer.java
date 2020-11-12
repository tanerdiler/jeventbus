package jeventbus.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import jeventbus.streaming.EventMessage;

public class JsonDeserializer {

    private final ObjectMapper objectMapper;

    public JsonDeserializer() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public EventMessage deserialize(byte[] data) {
        EventMessage message = null;
        try {
            message = this.objectMapper.readValue(data, EventMessage.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes "+ exception);
        }
        return message;
    }
}
