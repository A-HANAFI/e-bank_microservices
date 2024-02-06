package net.ahmed.bank.loans.service;

import net.ahmed.bank.loans.DTO.LoanDto;
import net.ahmed.bank.loans.entity.Loan;

public interface LoanService {
    Loan createLoan(String phoneNumber);
    boolean updateLoan(LoanDto loanDto);

    LoanDto fetchLoan(String phoneNumber);

    boolean deleteLoan(String phoneNumber);
}
