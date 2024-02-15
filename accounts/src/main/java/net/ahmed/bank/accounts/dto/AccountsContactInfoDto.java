package net.ahmed.bank.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "contacts")
@Getter @Setter
public class AccountsContactInfoDto {
    private String message;
    private Map<String ,String> contactDetails;
    private List<String> onCallSupport;
}
