package net.ahmed.bank.loans.entity;

import jakarta.persistence.*;
import lombok.*;
import net.ahmed.bank.loans.enums.LoanType;

@Entity
@Table(name = "loans")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
public class Loan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer loanId;

    @Column(name = "mobile_number")
    String mobileNumber;

    @Column(name = "loan_number")
    String loanNumber;

    @Column(name = "loan_type",columnDefinition = "ENUM('CAR', 'HOUSE', 'EDUCATION')")
    @Enumerated(EnumType.STRING)
    LoanType loanType;

    @Column(name = "total_loan")
    int totalLoan;

    @Column(name = "amount_paid")
    int amountPaid;

    @Column(name = "outstanding_amount")
    int outstandingAmount;




}
