package jeventbus.kafka.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import jeventbus.streaming.ActorType;

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