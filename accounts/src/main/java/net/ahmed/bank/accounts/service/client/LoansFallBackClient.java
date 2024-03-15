package net.ahmed.bank.accounts.service.client;

import net.ahmed.bank.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallBackClient implements LoansFeignClient{
    /**
     * @param correlationId
     * @param mobileNumber
     * @return
     */
    @Override
    public ResponseEntity<LoanDto> findLoan(String correlationId, String mobileNumber) {
        return null;
    }
}
