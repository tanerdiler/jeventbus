module jeventbus.example.kafka {
    exports jeventbus.kafka.example to com.fasterxml.jackson.databind;
    exports jeventbus.kafka.example.business to jeventbus.core;
    requires jeventbus.kafka;
    requires jeventbus.core;
    requires jeventbus.enterprise;
    requires com.fasterxml.jackson.annotation;

}