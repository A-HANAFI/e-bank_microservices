package net.ahmed.bank.accounts.service.client;

import net.ahmed.bank.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallBackClient implements CardsFeignClient{
    /**
     * @param correlationId
     * @param mobileNumber
     * @return
     */
    @Override
    public ResponseEntity<CardsDto> getCard(String correlationId, String mobileNumber) {
        return null;
    }
}
