package jeventbus.rabbitmq.example;

import jeventbus.rabbitmq.example.business.BankAccount;
import jeventbus.core.Events;
import jeventbus.rabbitmq.conf.ConnectionOption;
import jeventbus.rabbitmq.conf.QueueOption;
import jeventbus.rabbitmq.consumer.RabbitMqEventConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EventConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        var bankAccount = new BankAccount();
        var conf = ConnectionOption.aNew()
                .host("localhost")
                .username("test")
                .password("test")
                .virtualHost("workshop-vhost")
                .queue(QueueOption.aNew()
                        .name("test")
                        .autoDelete(false)
                        .durable(true)
                        .exclusive(false)
                        .get())
                .get();

        Events.event(MyEventType.PAYMENT_RECEIVED).add(bankAccount);

        RabbitMqEventConsumer consumer = new RabbitMqEventConsumer(conf);
        consumer.consume();
    }
}
