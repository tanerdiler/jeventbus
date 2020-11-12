import jeventbus.streaming.ActorType;

public enum MyActorType implements ActorType<MyActorType> {
    BUYER;

    @Override
    public MyActorType fromName(String name) {
        return BUYER;
    }
}
