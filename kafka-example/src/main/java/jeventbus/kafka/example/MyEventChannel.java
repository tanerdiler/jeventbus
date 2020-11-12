package jeventbus.kafka.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import jeventbus.streaming.EventChannel;

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