1.	Shemiramoth Mugo – SCT212-0061/2023 (Group Leader)
2.	Lee Kariuki – SCT212-0049/2023
3.	Cecil Kioko – SCT212-0047/2023
4.	Samuel Kuria – SCT212-0476/2023

**Object-Oriented Design (OOD) for the Financial Management Module**

**1\. Overview of the Finance Module**

The Finance Module is designed to handle payments, loan management, transaction tracking, and bulk transactions. It ensures secure and efficient financial operations for farmers, collectors, and cooperatives, while integrating with mobile money systems like M-Pesa. The module uses Spring Boot for backend development, PostgreSQL as the database, and JPA for ORM to ensure scalability and maintainability.

**2\. Class Structure (Attributes & Methods)**

**A) Financial Record System (Abstract Parent Class)**

**Class: FinancialRecord (Abstract)**

- id: Long – Unique identifier for the financial record.
- amount: BigDecimal – Transaction or loan amount.
- timestamp: LocalDateTime – Date and time of the transaction or loan.

**Methods**:

- displayRecord(): void – Abstract method to display record details.

**Purpose**:

- Acts as a base class for entities like Transaction and Loan.
- Encapsulates shared attributes (id, amount, timestamp) and behavior (displayRecord()).

**B) Payment System**

**Interface: PaymentGateway (Polymorphism)**  
**Methods**:

- processPayment(amount: BigDecimal, sender: String, receiver: String): Transaction

**Classes**:

- **MpesaPayment (Implements PaymentGateway)**  
    **Methods**:
  - processPayment(amount, sender, receiver): Executes payments via M-Pesa.

**Purpose**:

- Provides flexibility to support multiple payment methods (e.g., M-Pesa, Bank Transfer).
- Implements the **Strategy Pattern** to allow dynamic switching between payment gateways.

**C) Transaction Management**

**Class: Transaction (Extends FinancialRecord)**

- type: String – Type of transaction (e.g., "Mpesa Payment").
- sender: String – Entity sending the money.
- receiver: String – Entity receiving the money.

**Methods**:

- getTransactionId(): String
- displayRecord(): void – Overrides displayRecord() to show transaction-specific details.

**Repository**:

- **TransactionRepository (Extends JpaRepository&lt;Transaction, Long&gt;)**  
    **Methods**:
  - save(transaction: Transaction): Transaction
  - findById(id: Long): Optional&lt;Transaction&gt;
  - findAll(): List&lt;Transaction&gt;

**Controller**:

- **FinanceController**  
    **Endpoints**:
  - POST /api/transactions/pay: Initiates a payment.
  - GET /api/transactions/history: Retrieves all transactions.

**Purpose**:

- Manages transactions using the **Repository Pattern** for database interactions.
- Exposes RESTful APIs for payment processing and transaction history retrieval.

**D) Loan Management**

**Class: Loan (Extends FinancialRecord)**

- farmerId: String – Farmer applying for the loan.
- interestRate: BigDecimal – Loan interest rate (10%).
- repaymentMonths: int – Loan repayment duration (3 months).
- isPaidOff: boolean – Indicates if the loan is fully repaid.
- remainingAmount: BigDecimal – Remaining balance after repayments.

**Methods**:

- deductInstallment(amountPaid: BigDecimal): Deducts an installment from the loan balance.
- displayRecord(): void – Overrides displayRecord() to show loan-specific details.

**Repository**:

- **LoanRepository (Extends JpaRepository&lt;Loan, Long&gt;)**  
    **Methods**:
  - save(loan: Loan): Loan
  - findById(id: Long): Optional&lt;Loan&gt;

**Service**:

- **LoanService**  
    **Methods**:
  - applyForLoan(farmerId: String, principalAmount: BigDecimal): Loan
  - makeRepayment(loanId: Long, amountPaid: BigDecimal): Loan
  - checkLoanStatus(loanId: Long): Loan

**Controller**:

- **LoanController**  
    **Endpoints**:
  - POST /api/loan/apply: Farmers apply for a loan.
  - POST /api/loan/repay: Makes a loan repayment.
  - GET /api/loan/status/{id}: Checks loan status.

**Purpose**:

- Manages loans using the **Repository Pattern** for database interactions.
- Provides APIs for loan application, repayment, and status checking.

**E) Error Handling & Validation**

**Class: PaymentException**

- Extends Exception.
- Used to handle invalid payments (e.g., negative amounts).

**Methods**:

- PaymentException(String message): Constructor to initialize the exception with a custom message.

**Purpose**:

- Implements robust error handling using **Custom Exceptions**.
- Ensures invalid inputs are handled gracefully.

**3\. API Endpoints**

| **HTTP Method** | **Endpoint** | **Description** | **Parameters** |     |
| --- | --- | --- | --- | --- |
| POST | /api/transactions/pay | Initiates a payment. | amount, sender, receiver |     |
| GET | /api/transactions/history | Retrieves all transactions. | None |     |
| POST | /api/loan/apply | Farmers apply for a loan. | farmerId, principalAmount |     |
| POST | /api/loan/repay | Makes a loan repayment. | loanId, amountPaid |     |
| GET | /api/loan/status/{id} | Checks the status of a specific loan. | id  |     |

**5\. Final Summary**

- **Encapsulation, Abstraction & Polymorphism** are used effectively.
- **Database Integration** with JPA and PostgreSQL ensures scalability.
- **Error Handling** with custom exceptions.
- **API Endpoints** provide RESTful interactions for transactions and loans.
- **Concurrency** in payment processing ensures smooth operations.
