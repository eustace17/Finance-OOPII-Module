package finance.module.payment;

import finance.module.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MpesaPayment implements PaymentGateway {

    @Override
    public Transaction processPayment(BigDecimal amount, String sender, String receiver) throws PaymentException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Invalid payment amount!");
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setType("Mpesa Payment");
        return transaction;
    }
}