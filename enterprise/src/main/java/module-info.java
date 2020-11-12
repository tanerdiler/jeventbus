module jeventbus.enterprise {
    exports jeventbus.streaming;
    exports jeventbus.serialization;
    requires jeventbus.core;

    requires com.fasterxml.jackson.databind; // auto module
    opens jeventbus.streaming to com.fasterxml.jackson.databind;
}