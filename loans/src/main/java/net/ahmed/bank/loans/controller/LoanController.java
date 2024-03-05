package net.ahmed.bank.loans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ahmed.bank.loans.DTO.ErrorResponseDTO;
import net.ahmed.bank.loans.DTO.LoanDto;
import net.ahmed.bank.loans.DTO.LoansContactInfoDTO;
import net.ahmed.bank.loans.DTO.ResponseDTO;
import net.ahmed.bank.loans.constant.LoanConstants;
import net.ahmed.bank.loans.entity.Loan;
import net.ahmed.bank.loans.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@Validated
@RequestMapping("/api")
public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    LoanService loanService;

    @Value("${build.version}")
    private String buildVersion;

    LoansContactInfoDTO loansContactInfoDTO;

    private Environment environment;

    public LoanController(LoanService loanService, LoansContactInfoDTO loansContactInfoDTO, Environment environment) {
        this.loanService = loanService;
        this.loansContactInfoDTO = loansContactInfoDTO;
        this.environment = environment;
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
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> build(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
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
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> javaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
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
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDTO> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDTO);
    }

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoan(@Valid @RequestParam String mobileNumber){
        Loan loan = loanService.createLoan(mobileNumber);
        if(loan != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(LoanConstants.STATUS_201,LoanConstants.MESSAGE_201));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ResponseDTO("500","loan could not be created"));
        }
    }
    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateLoan(@Valid @RequestBody LoanDto loanDto){
        boolean isUpdated = loanService.updateLoan(loanDto);
        if(isUpdated){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(LoanConstants.STATUS_200,"loan updated successfully"));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(LoanConstants.STATUS_417,LoanConstants.MESSAGE_417_UPDATE));
        }
    }
    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> findLoan(@RequestHeader("ahmedbank-correlation-id") String  correlationId,
                                            @RequestParam String mobileNumber){
        logger.debug("ahmed bank correlation id found: {}", correlationId);
        LoanDto loanDto =  loanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loanDto);
    }
    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoan(@RequestParam String mobileNumber){
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_DELETE));
        }
    }

}
