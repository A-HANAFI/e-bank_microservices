package net.ahmed.bank.loans.service.serviceImpl;

import lombok.AllArgsConstructor;
import net.ahmed.bank.loans.DTO.LoanDto;
import net.ahmed.bank.loans.constant.LoanConstants;
import net.ahmed.bank.loans.entity.Loan;
import net.ahmed.bank.loans.enums.LoanType;
import net.ahmed.bank.loans.exception.LoanAlreadyExistsException;
import net.ahmed.bank.loans.exception.ResourceNotFoundException;
import net.ahmed.bank.loans.mapper.LoanMapper;
import net.ahmed.bank.loans.repository.LoanRepository;
import net.ahmed.bank.loans.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@AllArgsConstructor @Service
public class LoanServiceImpl implements LoanService {
    LoanRepository loanRepository;
    /**
     * @param mobileNumber
     * @return Loan
     */
    @Override
    public Loan createLoan(String mobileNumber) {
        Optional<Loan> loan = loanRepository.findLoanByMobileNumber(mobileNumber);
        if (loan.isPresent()){
            throw new LoanAlreadyExistsException("loan already exists with given phone number");
        }
        return loanRepository.save(createNewLoan(mobileNumber));
    }
    private Loan createNewLoan(String mobileNumber){
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanType.HOUSE);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    /**
     * @param loanDto
     * @return
     */
    @Override
    public boolean updateLoan(LoanDto loanDto) {

        Loan loan = loanRepository.findLoanByLoanNumber(loanDto.getLoanNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber()));
        LoanMapper.mapToLoan(loanDto,loan);
        loanRepository.save(loan);
        return true;
    }

    /**
     * @param phoneNumber
     */
    @Override
    public LoanDto fetchLoan(String phoneNumber) {
        Loan loan = loanRepository.findLoanByMobileNumber(phoneNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","phone number",phoneNumber)
        );
        return LoanMapper.mapToLoanDTO(loan, new LoanDto());
    }

    /**
     * @param phoneNumber
     * @return
     */
    @Override
    public boolean deleteLoan(String phoneNumber) {
        Loan loan = loanRepository.findLoanByMobileNumber(phoneNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","phone number", phoneNumber)
        )        ;
         loanRepository.delete(loan);
        return true;
    }
}
