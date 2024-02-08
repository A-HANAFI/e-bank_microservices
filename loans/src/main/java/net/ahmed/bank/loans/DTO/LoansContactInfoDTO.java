package net.ahmed.bank.loans.DTO;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix="contacts")
public record LoansContactInfoDTO(String message, Map<String,String> contactDetails, List<String> phones) {
}
