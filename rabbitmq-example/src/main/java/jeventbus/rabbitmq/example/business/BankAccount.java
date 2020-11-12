package jeventbus.rabbitmq.example.business;

import jeventbus.shared.EventSource;
import jeventbus.rabbitmq.example.listener.PaymentReceivedListener;

import java.math.BigDecimal;

import static java.lang.String.format;

public class BankAccount implements PaymentReceivedListener {

    private BigDecimal amount = new BigDecimal("0.0");

    @Override
    public void onPaymentReceived(EventSource source) {
        var amount = (BigDecimal) source.get("PRICE");
        this.amount = this.amount.add(amount);
        System.out.println(format("BankAccount :: triggered :: onPaymentReceived(amount:%.2f) :: increased to %.2f", amount, this.amount));
    }
}
