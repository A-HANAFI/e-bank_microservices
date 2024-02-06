package net.ahmed.bank.loans.repository;

import net.ahmed.bank.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findLoanByLoanNumber(String loanNumber);
    Optional<Loan> findLoanByMobileNumber(String mobileNumber);
}
