package jeventbus.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jeventbus.core.Events;
import jeventbus.rabbitmq.conf.ConnectionOption;
import jeventbus.serialization.JsonDeserializer;
import jeventbus.streaming.EventToMessageConverter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqEventConsumer {

    private static final Long POLLING_DELAY = 100L;

    private final ConnectionOption conf;

    private JsonDeserializer deserializer = new JsonDeserializer();

    public RabbitMqEventConsumer(ConnectionOption conf) {
        this.conf = conf;
    }

    public void consume() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(conf.getHost());
        factory.setPassword(conf.getPassword());
        factory.setUsername(conf.getUsername());
        factory.setVirtualHost(conf.getVirtualHost());

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.basicQos(conf.getUnackedMessageCountToAcceptAtATime()); // accept only one unack-ed message at a time (see below)

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                var message = deserializer.deserialize(delivery.getBody());
                var source = EventToMessageConverter.convert(message);
                Events.event(message.getEvent()).fire(source);
            } catch (Exception e) {
                // TODO : convert to app exception
                e.printStackTrace();
            } finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(conf.getQueue().getName(), autoAck, deliverCallback, consumerTag -> {
        });
    }
}
