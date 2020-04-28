package jeventbus.kafka.producer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jeventbus.kafka.KafkaEventSerializer;
import jeventbus.shared.EventSource;
import jeventbus.shared.EventType;
import jeventbus.streaming.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jeventbus.kafka.producer.KafkaEventProducer.MyEventContext.MAIL;
import static jeventbus.kafka.producer.KafkaEventProducer.MyEventContext.PAYMENT;
import static jeventbus.kafka.producer.KafkaEventProducer.MyEventType.PAYMENT_RECEIVED;
import static jeventbus.shared.Parameter.by;

public class KafkaEventProducer {

    private Producer<String, EventMessage> producer;

    public enum MyEventReason implements EventReason<MyEventReason> {
        BUY_SOMETHING, SELL_SOMETHING;

        @Override
        public MyEventReason fromName(String name) {
            return null;
        }
    }

    public enum MyActorType implements ActorType<MyActorType> {
        @JsonProperty("BUYER")
        BUYER,
        @JsonProperty("SELLER")
        SELLER;

        @Override
        public MyActorType fromName(String name) {
            return MyActorType.valueOf(name);
        }
    }

    public enum MyEventChannel implements EventChannel<MyEventChannel> {
        @JsonProperty("WEB")
        WEB,
        @JsonProperty("POST")
        POST,
        @JsonProperty("ATM")
        ATM;

        @Override
        public MyEventChannel fromName(String name) {
            return valueOf(name);
        }
    }

    public enum MyEventType implements EventType {
        @JsonProperty("MAIL_RECEIVED")
        MAIL_RECEIVED("onMailReceived"),
        @JsonProperty("PAYMENT_RECEIVED")
        PAYMENT_RECEIVED("onPaymentReceived");

        private String methodName;

        MyEventType(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public String getMethodName() {
            return methodName;
        }
    }

    public enum MyEventContext implements EventContext {
        @JsonProperty("MAIL")
        MAIL,
        @JsonProperty("PAYMENT")
        PAYMENT;

        @Override
        public EventContext fromName(String name) {
            return valueOf(name);
        }
    }

    public enum MyEventDefinition implements EventDefinition {
        @JsonProperty("MAIL_RECEIVED")
        MAIL_RECEIVED(1, MAIL, true, MyEventType.MAIL_RECEIVED),
        @JsonProperty("PAYMENT_RECEIVED")
        PAYMENT_RECEIVED(2, PAYMENT, true, MyEventType.PAYMENT_RECEIVED);

        private final int id;

        private final MyEventContext context;

        private final boolean reportable;

        private final MyEventType eventType;

        MyEventDefinition(int id, MyEventContext context, boolean reportable, MyEventType eventType) {

            this.id = id;
            this.context = context;
            this.reportable = reportable;
            this.eventType = eventType;
        }

        public static Optional<? extends EventDefinition> of(MyEventType eventType) {
            return Arrays.stream(values()).filter(d->eventType==d.getEventType()).findAny();
        }

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public EventContext getContext() {
            return context;
        }

        @Override
        public Boolean getReportable() {
            return reportable;
        }

        @Override
        public EventType getEventType() {
            return eventType;
        }
    }

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

    public static void main(String[] args) {
        EventDefinition definition = MyEventDefinition.of(PAYMENT_RECEIVED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(PAYMENT_RECEIVED,
                                              by(EventSourceKeys.EVENT_CHANNEL, MyEventChannel.ATM),
                                              by(EventSourceKeys.ACTOR_ID, 1L),
                                              by(EventSourceKeys.ACTOR_TYPE, MyActorType.BUYER),
                                              by(EventSourceKeys.EVENT_REASON, MyEventReason.BUY_SOMETHING),
                                              by("PRICE",123000),
                                              by("FULLNAME", "Taner Diler"),
                                              by("PAYMENT_METHOD", "CREDIT_CARD"));
        EventMessage message = EventToMessageConverter.convert(PAYMENT_RECEIVED, source, definition);
        EventMessage message1 = EventToMessageConverter.convert(PAYMENT_RECEIVED, source, definition);



        KafkaEventProducer producer = new KafkaEventProducer().connect();
        while (true) {

            producer.produce(message);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
