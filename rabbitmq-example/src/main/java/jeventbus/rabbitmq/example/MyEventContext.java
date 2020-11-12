package jeventbus.rabbitmq.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import jeventbus.streaming.EventContext;

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