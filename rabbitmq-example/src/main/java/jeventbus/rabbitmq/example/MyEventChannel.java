package jeventbus.rabbitmq.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import jeventbus.streaming.EventChannel;

public enum MyEventChannel implements EventChannel {
    @JsonProperty("WEB")
    WEB,
    @JsonProperty("POST")
    POST,
    @JsonProperty("ATM")
    ATM;

    @Override
    public EventChannel fromName(String name) {
        return valueOf(name);
    }
}