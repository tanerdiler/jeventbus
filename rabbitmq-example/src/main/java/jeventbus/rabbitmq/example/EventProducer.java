package jeventbus.rabbitmq.example;

import jeventbus.rabbitmq.conf.ConnectionOption;
import jeventbus.rabbitmq.conf.QueueOption;
import jeventbus.rabbitmq.producer.RabbitMqEventProducer;
import jeventbus.shared.EventSource;
import jeventbus.streaming.EventDefinition;
import jeventbus.streaming.EventMessage;
import jeventbus.streaming.EventSourceKeys;
import jeventbus.streaming.EventToMessageConverter;

import java.math.BigDecimal;

import static jeventbus.shared.Parameter.by;

public class EventProducer {
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

        var conf = ConnectionOption.aNew()
                .host("localhost")
                .username("test")
                .password("test")
                .virtualHost("workshop-vhost")
                .queue(QueueOption.aNew()
                        .name("test")
                        .autoDelete(false)
                        .durable(true)
                        .exclusive(false)
                        .get())
                .get();

        RabbitMqEventProducer producer = new RabbitMqEventProducer(conf).connect();
        while (true) {

            producer.produce(message);
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
