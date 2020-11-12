package jeventbus.rabbitmq.example;

import jeventbus.streaming.EventReason;

public enum MyEventReason implements EventReason<MyEventReason> {
    BUY_SOMETHING, SELL_SOMETHING;

    @Override
    public MyEventReason fromName(String name) {
        return null;
    }
}









