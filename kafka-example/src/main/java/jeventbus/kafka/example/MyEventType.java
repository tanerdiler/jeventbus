package jeventbus.kafka.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import jeventbus.shared.EventType;

public enum MyEventType implements EventType {
    @JsonProperty("MAIL_RECEIVED")
    MAIL_RECEIVED("onMailReceived"),
    @JsonProperty("PAYMENT_RECEIVED")
    PAYMENT_RECEIVED("onPaymentReceived");

    private String methodName;

    MyEventType(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }
}