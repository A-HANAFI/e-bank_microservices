package net.ahmed.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name="customer",
        description = "Schema to hold customer and accounts information")
@Data
public class CustomerDetailsDto {

    @Schema(description = "name of the customer",
            example="ahmed hanafi")
    @NotNull(message = "name can't be empty")
    @Size(min = 5, max=30, message = "name should be between 5 an 30 character long")
    private String  name;

    @Schema(description = "email of the customer",
            example="ahmed.hanafi@test.com")
    @NotNull(message = "email can't be empty")
    @Email(message="email address should be a valid value")
    private String email;

    @Schema(description = "phone number of the customer",
            example="6242611606")
    @NotNull(message = "Mobile Number can't be empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private String  mobileNumber;


    AccountsDto accountsDto;

    CardsDto cardsDto;

    LoanDto loansDto;
}
