package finance.module.api;

import finance.module.model.Transaction;
import finance.module.payment.PaymentException;
import finance.module.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class FinanceController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> processPayment(
            @RequestParam BigDecimal amount,
            @RequestParam String sender,
            @RequestParam String receiver) {
        try {
            Transaction transaction = paymentService.processTransaction(amount, sender, receiver);
            return ResponseEntity.ok(transaction);
        } catch (PaymentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getAllTransactions() {
        return ResponseEntity.ok(paymentService.getAllTransactions());
    }
}
