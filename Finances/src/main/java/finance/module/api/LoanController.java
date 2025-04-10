package finance.module.api;

import finance.module.model.Loan;
import finance.module.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Apply for a loan
    @PostMapping("/apply")
    public ResponseEntity<?> applyForLoan(
            @RequestParam String farmerId,
            @RequestParam BigDecimal principalAmount) {
        Loan loan = loanService.applyForLoan(farmerId, principalAmount);
        return ResponseEntity.ok(loan);
    }

    // Make a loan repayment
    @PostMapping("/repay")
    public ResponseEntity<?> makeRepayment(
            @RequestParam Long loanId,
            @RequestParam BigDecimal amountPaid) {
        Loan loan = loanService.makeRepayment(loanId, amountPaid);
        return ResponseEntity.ok(loan);
    }

    // Check loan status (including remaining balance)
    @GetMapping("/status/{id}")
    public ResponseEntity<?> checkLoanStatus(@PathVariable Long id) {
        Loan loan = loanService.checkLoanStatus(id);
        return ResponseEntity.ok(loan);
    }
}