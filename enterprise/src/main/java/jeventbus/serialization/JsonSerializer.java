package jeventbus.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import jeventbus.streaming.EventMessage;

public class JsonSerializer {

    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public byte[] serialize(EventMessage message) {
        byte[] retVal = null;
        try {
            retVal = objectMapper.writeValueAsString(message).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
