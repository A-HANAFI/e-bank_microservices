package net.ahmed.bank.accounts.service.client;

import net.ahmed.bank.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="cards", fallback = CardsFallBackClient.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> getCard(@RequestHeader("ahmedbank-correlation-id") String  correlationId,
                                            @RequestParam String mobileNumber);
}
