package jeventbus.serialization;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonDeserializerTest {

    @Test
    @Disabled
    void test() {
        var json = "[\"jeventbus.streaming.EventMessage\",{\"traceId\":null,\"spanId\":null,\"actorId\":1,\"actorType\":[\"MyActorType\",\"BUYER\"],\"eventId\":2,\"entityId\":null,\"channel\":[\"MyEventChannel\",\"ATM\"],\"reason\":[\"MyEventReason\",\"BUY_SOMETHING\"],\"context\":[\"MyEventContext\",\"PAYMENT\"],\"event\":[\"MyEventType\",\"PAYMENT_RECEIVED\"],\"reportable\":true,\"detail\":[\"java.util.HashMap\",{\"EVENT_CHANNEL\":[\"MyEventChannel\",\"ATM\"],\"ACTOR_ID\":[\"java.lang.Long\",1],\"PRICE\":[\"java.math.BigDecimal\",100],\"ACTOR_TYPE\":[\"MyActorType\",\"BUYER\"}]";
        var deserializer = new JsonDeserializer();
        var message = deserializer.deserialize(json.getBytes());
        assertEquals("xxx", message.getChannel().name());
    }

}