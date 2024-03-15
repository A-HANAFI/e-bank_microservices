package net.ahmed.bank.accounts.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import net.ahmed.bank.accounts.constants.AccountsConstants;
import net.ahmed.bank.accounts.dto.AccountsContactInfoDto;
import net.ahmed.bank.accounts.dto.CustomerDto;
import net.ahmed.bank.accounts.dto.ErrorResponseDto;
import net.ahmed.bank.accounts.dto.ResponseDto;
import net.ahmed.bank.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@Tag(name = "CRUD REST APIs for Accounts in EAZYBANK",
description = "CRUD REST APIs for Accounts in EAZYBANK, create, update, show accounts details and delete"
)
@RestController
@RequestMapping(path = "/api")
//@AllArgsConstructor
@Validated
public class AccountsController {

    private static final Logger logger =   LoggerFactory.getLogger(AccountsController.class);
    private final IAccountsService iAccountsService;

    @Value("${build.version}")
    private String buildVersion;

    private Environment environment;

    AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(IAccountsService iAccountsService, Environment environment, AccountsContactInfoDto accountsContactInfoDto) {
        this.iAccountsService = iAccountsService;
        this.environment = environment;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Operation(summary = "retrieve the build version",
            description = "CRUD REST API fetch the build version")
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

    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallBack") //retry pattern implementation
    @GetMapping("/build-info") //path for get method
    public ResponseEntity<String> getBuildInfo() throws TimeoutException {
        logger.debug("accounts build info");
        throw new TimeoutException();
//        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallBack(Throwable throwable){ // the method to invoke after number of retries reached
        logger.debug("accounts build info fallBack");
        return ResponseEntity.status(HttpStatus.OK).body("0.9");
    }

    @Operation(summary = "retrieve the JAVA version",
            description = "CRUD REST API fetch the JAVA version")
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
    @RateLimiter(name= "getJavaVersion", fallbackMethod = "getJavaVersionFallback")//implementing RateLimiter pattern inside microservice
    @GetMapping("/java-version")
    public ResponseEntity<String> javaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }
    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java 17");
    }

    @Operation(summary = "retrieve the developer contact info",
            description = "CRUD REST API fetch the developer contact info")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Found"
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
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK).body(accountsContactInfoDto);
    }

    @Operation(summary = "create an account",
            description = "CRUD REST API to create, update, fetch and delete an account and a customer ")
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
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201 , AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "retrieve an account",
            description = "CRUD REST API fetch an account and a customer based on account number")
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
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails( @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
                                                               @RequestParam String mobileNumber){
        return ResponseEntity.status(HttpStatus.OK).body(iAccountsService.fetchAccount(mobileNumber));
    }
    @Operation(
            summary = "Update account detail REST API",
            description = "REST API to update customer and account details based on account customer"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Http status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto ){
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_update));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(
            @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
                                                          @RequestParam String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, "account deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_delete));
        }
        }
    }

