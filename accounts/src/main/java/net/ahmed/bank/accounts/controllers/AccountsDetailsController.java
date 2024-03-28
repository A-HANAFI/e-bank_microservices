package net.ahmed.bank.accounts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import net.ahmed.bank.accounts.dto.CustomerDetailsDto;
import net.ahmed.bank.accounts.dto.ErrorResponseDto;
import net.ahmed.bank.accounts.service.ICustomerDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CRUD REST APIs for Accounts in Ahmed bank",
        description = "CRUD REST APIs for Accounts in Ahmed bank, create, update, show accounts details and delete"
)

@RestController
@Validated
@RequestMapping(path = "/api")
public class AccountsDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsDetailsController.class);
    ICustomerDetailsService iCustomerDetailsService;

    public AccountsDetailsController(ICustomerDetailsService iCustomerDetailsService) {
        this.iCustomerDetailsService = iCustomerDetailsService;
    }

    @Operation(summary = "retrieve an account details",
            description = "CRUD REST API fetch an account and a customer, loans and cards based on mobile number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchAccountDetails(@RequestHeader("ahmedbank-correlation-id") String  correlationId,
            @Pattern(regexp="(^$|[0-9]{10})",message = "mobile number must be 10 digits")
                                                           @RequestParam String mobileNumber){
//        logger.debug("ahmed bank correlation id found: {}", correlationId);
        logger.debug("fetchCustomerDetails method started");
        CustomerDetailsDto customerDetailsDto = iCustomerDetailsService.fetchCustomerDetails(mobileNumber,correlationId);
        logger.debug("fetchCustomerDetails method ended");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
