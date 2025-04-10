package finance.module.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String farmerId; // Farmer applying for the loan
    private BigDecimal principalAmount; // Loan principal amount
    private BigDecimal interestRate; // Interest rate (10%)
    private int repaymentMonths; // Loan repayment duration (3 months)
    private boolean isPaidOff; // Indicates if the loan is fully repaid
    private LocalDate issueDate; // Date the loan was issued
    private LocalDate dueDate; // Due date for repayment
    private BigDecimal remainingBalance; // Remaining balance after repayments

    public Loan() {
        this.interestRate = BigDecimal.valueOf(10); // Default interest rate: 10%
        this.repaymentMonths = 3; // Default repayment duration: 3 months
        this.isPaidOff = false;
    }

    // Calculate total loan amount (principal + interest)
    public BigDecimal getTotalAmount() {
        BigDecimal interest = principalAmount.multiply(interestRate).divide(BigDecimal.valueOf(100));
        return principalAmount.add(interest);
    }

    // Initialize remaining balance when the loan is created
    public void initializeRemainingBalance() {
        this.remainingBalance = getTotalAmount();
    }

    // Deduct installment payment and update remaining balance
    public void deductInstallment(BigDecimal amountPaid) {
        if (amountPaid.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }
        if (amountPaid.compareTo(remainingBalance) > 0) {
            throw new IllegalArgumentException("Payment amount exceeds remaining balance.");
        }
        remainingBalance = remainingBalance.subtract(amountPaid);
        if (remainingBalance.compareTo(BigDecimal.ZERO) == 0) {
            this.isPaidOff = true;
        }
    }
}