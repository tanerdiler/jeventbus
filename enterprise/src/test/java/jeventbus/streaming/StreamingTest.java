package jeventbus.streaming;

import jeventbus.shared.EventSource;
import jeventbus.shared.EventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static jeventbus.shared.Parameter.by;
import static jeventbus.streaming.StreamingTest.MyEventContext.MAIL;
import static jeventbus.streaming.StreamingTest.MyEventContext.PAYMENT;
import static jeventbus.streaming.StreamingTest.MyEventType.PAYMENT_RECEIVED;

public class StreamingTest {

    public enum MyEventReason implements EventReason<MyEventReason> {
        BUY_SOMETHING, SELL_SOMETHING;

        @Override
        public MyEventReason fromName(String name) {
            return null;
        }
    }

    public enum MyActorType implements ActorType<MyActorType> {
        BUYER, SELLER;

        @Override
        public MyActorType fromName(String name) {
            return null;
        }
    }

    public enum MyEventChannel implements EventChannel<MyEventChannel> {
        WEB, POST, ATM;

        @Override
        public MyEventChannel fromName(String name) {
            return valueOf(name);
        }
    }

    public enum MyEventType implements EventType {
        MAIL_RECEIVED("onMailReceived"),
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
        MAIL, PAYMENT;

        @Override
        public EventContext fromName(String name) {
            return valueOf(name);
        }
    }

    public enum MyEventDefinition implements EventDefinition {
        MAIL_RECEIVED(1, MAIL, true, MyEventType.MAIL_RECEIVED),
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


    @Test
    public void should_do_it_test() {
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
        Assertions.assertEquals(message1.toString(), message.toString());
    }
}
