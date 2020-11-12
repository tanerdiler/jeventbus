package jeventbus.rabbitmq.conf;

public class ConnectionOptionBuilder {

    private String host;
    private String username;
    private String password;
    private String virtualHost;
    private QueueOption queueOption;
    private int unackedMessageCount = 1;

    public ConnectionOptionBuilder host(String host) {
        this.host = host;
        return this;
    }
    public ConnectionOptionBuilder username(String username) {
        this.username = username;
        return this;
    }
    public ConnectionOptionBuilder password(String password) {
        this.password = password;
        return this;
    }
    public ConnectionOptionBuilder virtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
        return this;
    }
    public ConnectionOptionBuilder queue(QueueOption queueOption) {
        this.queueOption = queueOption;
        return this;
    }
    public ConnectionOptionBuilder unackedCount(int unackedMessageCount) {
        if (unackedMessageCount < 1) {
            throw new IllegalArgumentException("unackedMessageCount couldn't be negative or zero!" );
        }
        this.unackedMessageCount = unackedMessageCount;
        return this;
    }

    public ConnectionOption get() {
        return new ConnectionOption(host, username, password, virtualHost, queueOption, unackedMessageCount);
    }
}
