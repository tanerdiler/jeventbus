package jeventbus.rabbitmq.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import jeventbus.shared.EventType;
import jeventbus.streaming.EventContext;
import jeventbus.streaming.EventDefinition;

import java.util.Arrays;
import java.util.Optional;

public enum MyEventDefinition implements EventDefinition {
    @JsonProperty("MAIL_RECEIVED")
    MAIL_RECEIVED(1, MyEventContext.MAIL, true, MyEventType.MAIL_RECEIVED),
    @JsonProperty("PAYMENT_RECEIVED")
    PAYMENT_RECEIVED(2, MyEventContext.PAYMENT, true, MyEventType.PAYMENT_RECEIVED);

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
        return Arrays.stream(values()).filter(d -> eventType == d.getEventType()).findAny();
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