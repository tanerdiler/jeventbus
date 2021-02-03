module jeventbus.core {
    exports jeventbus.core to jeventbus.enterprise, jeventbus.kafka, jeventbus.rabbitmq, jeventbus.example.kafka, jeventbus.example.rabbitmq;
    exports jeventbus.shared;
    exports jeventbus.service;
    opens jeventbus.shared to com.fasterxml.jackson.databind;
}