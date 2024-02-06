package net.ahmed.bank.loans.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ResponseDTO {
    private String statusCode;
    private String statusDescription;
}
