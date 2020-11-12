package jeventbus.rabbitmq.example;

import jeventbus.serialization.JsonDeserializer;
import jeventbus.serialization.JsonSerializer;
import jeventbus.shared.EventSource;
import jeventbus.streaming.*;

import java.math.BigDecimal;

import static jeventbus.shared.Parameter.by;

public class App {
    public static void main(String[] args) {

        EventDefinition definition = MyEventDefinition.of(MyEventType.PAYMENT_RECEIVED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(MyEventType.PAYMENT_RECEIVED,
                by(EventSourceKeys.EVENT_CHANNEL, MyEventChannel.ATM),
                by(EventSourceKeys.ACTOR_ID, 1L),
                by(EventSourceKeys.ACTOR_TYPE, MyActorType.BUYER),
                by(EventSourceKeys.EVENT_REASON, MyEventReason.BUY_SOMETHING),
                by("PRICE",new BigDecimal("100")),
                by("FULLNAME", "Taner Diler"),
                by("PAYMENT_METHOD", "CREDIT_CARD"));
        EventMessage message = EventToMessageConverter.convert(MyEventType.PAYMENT_RECEIVED, source, definition);
        EventMessage message2 = new EventMessage("asdfa","123q", 123L, MyActorType.BUYER, 1, 1L, MyEventChannel.ATM, null, null, MyEventType.MAIL_RECEIVED, true, null, null, null);
        var serializer = new JsonSerializer();
        var serialized = serializer.serialize(message);
        System.out.println(new String(serialized));
        var deserializer = new JsonDeserializer();
        var deserialized = deserializer.deserialize(serialized);
    }
}
