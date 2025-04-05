package finance.module.payment;

import finance.module.model.Transaction;
import java.math.BigDecimal;

public interface PaymentGateway {
    Transaction processPayment(BigDecimal amount) throws PaymentException;
}