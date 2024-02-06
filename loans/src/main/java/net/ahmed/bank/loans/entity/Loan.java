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
    Integer id;

    @Column(name = "mobile_number")
    String mobileNumber;

    @Column(name = "loan_number")
    String loanNumber;

    @Column(name = "loan_type")
    LoanType loanType;

    @Column(name = "total_loan")
    int totalLoan;

    @Column(name = "amount_paid")
    int amountPaid;

    @Column(name = "outstanding_amount")
    int outstandingAmount;




}
