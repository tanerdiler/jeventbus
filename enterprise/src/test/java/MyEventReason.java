import jeventbus.streaming.ActorType;
import jeventbus.streaming.EventReason;

public enum MyEventReason implements EventReason<MyEventReason> {
    BUY_SOMETHING;

    @Override
    public MyEventReason fromName(String name) {
        return BUY_SOMETHING;
    }
}
