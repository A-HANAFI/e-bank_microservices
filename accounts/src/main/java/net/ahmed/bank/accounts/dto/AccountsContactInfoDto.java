package net.ahmed.bank.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "contacts")
public record AccountsContactInfoDto(String message, Map<String ,String> contactDetails, List<String> onCallSupport) {
}
