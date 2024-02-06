package net.ahmed.bank.loans.mapper;

import net.ahmed.bank.loans.DTO.LoanDto;
import net.ahmed.bank.loans.entity.Loan;

public class LoanMapper {

    public static LoanDto mapToLoanDTO(Loan loan , LoanDto loanDto){
        loanDto.setMobileNumber( loan.getMobileNumber());
        loanDto.setLoanNumber(  loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setTotalLoan(   loan.getTotalLoan());
        loanDto.setAmountPaid(    loan.getAmountPaid());
        loanDto.setOutstandingAmount(      loan.getOutstandingAmount());
        return loanDto;
    }

    public static Loan mapToLoan(LoanDto loanDto , Loan loan){
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());
        return loan;
    }
}
