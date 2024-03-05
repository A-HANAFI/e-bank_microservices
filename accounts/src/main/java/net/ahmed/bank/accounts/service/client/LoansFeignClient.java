package net.ahmed.bank.accounts.service.client;

import net.ahmed.bank.accounts.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoanDto> findLoan(@RequestHeader("ahmedbank-correlation-id") String  correlationId,
                                            @RequestParam String mobileNumber);
}
