package net.ahmed.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Schema(name="Account",
        description = "Schema to hold accounts information")
@Data
public class AccountsDto {

    @Schema(description = "Account number",example = "9875826524")
    @NotEmpty(message = "Account number should not be empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private Long  accountNumber;

    @Schema(description = "Account Type",example = "Savings")
    @NotEmpty(message = "Account type can't be null or empty")
    private String accountType;

    @Schema(description = "Branch address",example = "New York")
    @NotEmpty(message = "Branch address can't be null or empty")
    private String branchAddress;
}
