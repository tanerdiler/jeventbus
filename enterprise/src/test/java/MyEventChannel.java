import jeventbus.streaming.ActorType;

public enum MyEventChannel implements ActorType<MyEventChannel> {
    ATM;

    @Override
    public MyEventChannel fromName(String name) {
        return ATM;
    }
}
