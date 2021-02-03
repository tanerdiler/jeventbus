module jeventbus.kafka {
    exports jeventbus.kafka.consumer;
    exports jeventbus.kafka.producer;

    requires jeventbus.enterprise;
    requires jeventbus.core;

    requires kafka.clients;
    exports jeventbus.kafka to kafka.clients;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;
}