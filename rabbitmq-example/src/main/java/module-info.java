module jeventbus.example.rabbitmq {
    requires jeventbus.rabbitmq;
    requires jeventbus.core;
    requires jeventbus.enterprise;
    requires com.fasterxml.jackson.annotation;
    exports jeventbus.rabbitmq.example to com.fasterxml.jackson.databind;
    opens jeventbus.rabbitmq.example.business to jeventbus.core;
}