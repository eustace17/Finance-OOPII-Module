package finance.module.service;

import finance.module.model.Loan;
import finance.module.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    // Apply for a loan
    public Loan applyForLoan(String farmerId, BigDecimal principalAmount) {
        Loan loan = new Loan();
        loan.setFarmerId(farmerId);
        loan.setPrincipalAmount(principalAmount);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusMonths(3)); // Due in 3 months
        loan.initializeRemainingBalance(); // Initialize remaining balance
        return loanRepository.save(loan);
    }

    // Make a loan repayment
    public Loan makeRepayment(Long loanId, BigDecimal amountPaid) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.deductInstallment(amountPaid); // Deduct installment and update balance
        return loanRepository.save(loan);
    }

    // Check loan status
    public Loan checkLoanStatus(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }
}