package finance.module.payment;

public class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }
}