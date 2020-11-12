package jeventbus.rabbitmq.conf;

public class QueueOptionBuilder {

    private String name;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;

    public QueueOptionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public QueueOptionBuilder durable(boolean durable) {
        this.durable = durable;
        return this;
    }

    public QueueOptionBuilder exclusive(boolean exclusive) {
        this.exclusive = exclusive;
        return this;
    }

    public QueueOptionBuilder autoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
        return this;
    }

    public QueueOption get() {
        return new QueueOption(name, durable, exclusive, autoDelete);
    }
}