package jeventbus.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jeventbus.rabbitmq.conf.ConnectionOption;
import jeventbus.serialization.JsonSerializer;
import jeventbus.streaming.EventMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqEventProducer {

    private final ConnectionOption conf;

    private Connection connection;
    private Channel channel;
    private JsonSerializer serializer = new JsonSerializer();

    public RabbitMqEventProducer(ConnectionOption conf) {
        this.conf = conf;
    }

    public RabbitMqEventProducer connect() {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(conf.getHost());
        factory.setPassword(conf.getPassword());
        factory.setUsername(conf.getUsername());
        factory.setVirtualHost(conf.getVirtualHost());

        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            var queue = conf.getQueue();
            channel.queueDeclare(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(), null);
        } catch (IOException | TimeoutException ex) {
            // TODO : convert to app exception
            throw new RuntimeException(ex);
        }

        return this;
    }


    public void produce(EventMessage eventMessage) {
        try {
            channel.basicPublish("", conf.getQueue().getName(), null, serializer.serialize(eventMessage));
        } catch (IOException e) {
            // TODO : convert to app exception
            e.printStackTrace();
        }
    }

}
