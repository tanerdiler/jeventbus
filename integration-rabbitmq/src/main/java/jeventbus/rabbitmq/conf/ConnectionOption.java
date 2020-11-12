package jeventbus.rabbitmq.conf;

public class ConnectionOption {

    private String host;
    private String username;
    private String password;
    private String virtualHost;
    private QueueOption queueOption;
    private int unackedMessageCount;

    ConnectionOption(String host, String username, String password, String virtualHost, QueueOption queueOption, int unackedMessageCount) {

        this.host = host;
        this.username = username;
        this.password = password;
        this.virtualHost = virtualHost;
        this.queueOption = queueOption;
        this.unackedMessageCount = unackedMessageCount;
    }

    public static ConnectionOptionBuilder aNew() {
        return new ConnectionOptionBuilder();
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public QueueOption getQueue() {
        return queueOption;
    }

    public int getUnackedMessageCountToAcceptAtATime() {
        return unackedMessageCount;
    }


}
