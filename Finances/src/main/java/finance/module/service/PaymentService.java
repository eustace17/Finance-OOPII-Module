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

    public Transaction processTransaction(BigDecimal amount, String sender, String receiver) throws PaymentException {
        if (sender == null || sender.isEmpty() || receiver == null || receiver.isEmpty()) {
            throw new PaymentException("Sender and receiver details are required!");
        }
        Transaction transaction = paymentGateway.processPayment(amount, sender, receiver);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}