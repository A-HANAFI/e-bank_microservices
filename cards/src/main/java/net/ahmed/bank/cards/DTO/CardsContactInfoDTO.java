package net.ahmed.bank.cards.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "contacts")
@Getter @Setter
public class CardsContactInfoDTO {
    private String message;
    private Map<String ,String> contactDetails;
    private List<String> onCallSupport;
}

