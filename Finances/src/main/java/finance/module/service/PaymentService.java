package finance.module.service;

import finance.module.model.Transaction;
import finance.module.payment.PaymentException;
import finance.module.payment.PaymentGateway;
import finance.module.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction processTransaction(BigDecimal amount) throws PaymentException {
        Transaction transaction = paymentGateway.processPayment(amount);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}