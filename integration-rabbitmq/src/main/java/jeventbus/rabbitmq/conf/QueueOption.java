package jeventbus.rabbitmq.conf;

public class QueueOption {

    private String name;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;

    QueueOption(String name, boolean durable, boolean exclusive, boolean autoDelete) {

        this.name = name;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
    }

    public static QueueOptionBuilder aNew() {
        return new QueueOptionBuilder();
    }

    public String getName() {
        return name;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

}