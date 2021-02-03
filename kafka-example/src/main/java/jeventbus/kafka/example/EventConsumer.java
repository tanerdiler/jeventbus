package jeventbus.kafka.example;

import jeventbus.kafka.example.business.BankAccount;
import jeventbus.core.Events;
import jeventbus.kafka.consumer.KafkaEventConsumer;

public class EventConsumer {
    public static void main(String[] args) {
        var bankAccount = new BankAccount();

        Events.event(MyEventType.PAYMENT_RECEIVED).add(bankAccount);

        KafkaEventConsumer consumer = new KafkaEventConsumer("localhost:9092", "user-tracking");
        consumer.consume();
    }
}
