module jeventbus.core {
    exports jeventbus.core;
    exports jeventbus.shared;
    opens jeventbus.shared to com.fasterxml.jackson.databind;
}