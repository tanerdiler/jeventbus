package jeventbus.rabbitmq.example.listener;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface PaymentReceivedListener extends EventListener {

    void onPaymentReceived(EventSource source);
}
