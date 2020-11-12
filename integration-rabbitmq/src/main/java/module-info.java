module jeventbus.rabbitmq {
    exports jeventbus.rabbitmq.consumer;
    exports jeventbus.rabbitmq.producer;
    exports jeventbus.rabbitmq.conf;

    requires jeventbus.core;
    requires jeventbus.enterprise;

    requires com.rabbitmq.client;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.slf4j;
}