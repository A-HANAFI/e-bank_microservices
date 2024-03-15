package net.ahmed.bank.accounts.service.impl;

import lombok.AllArgsConstructor;
import net.ahmed.bank.accounts.dto.AccountsDto;
import net.ahmed.bank.accounts.dto.CardsDto;
import net.ahmed.bank.accounts.dto.CustomerDetailsDto;
import net.ahmed.bank.accounts.dto.LoanDto;
import net.ahmed.bank.accounts.entity.Accounts;
import net.ahmed.bank.accounts.entity.Customer;
import net.ahmed.bank.accounts.exception.ResourceNotFoundException;
import net.ahmed.bank.accounts.mapper.AccountsMapper;
import net.ahmed.bank.accounts.mapper.CustomerMapper;
import net.ahmed.bank.accounts.repository.AccountsRepository;
import net.ahmed.bank.accounts.repository.CustomerRepository;
import net.ahmed.bank.accounts.service.ICustomerDetailsService;
import net.ahmed.bank.accounts.service.client.CardsFeignClient;
import net.ahmed.bank.accounts.service.client.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDetailsServiceImpl implements ICustomerDetailsService {
    CustomerRepository customerRepository;
    AccountsRepository accountsRepository;
    CardsFeignClient cardsFeignClient;
    LoansFeignClient loansFeignClient;
    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationID) {

        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("customer","Phone Number",mobileNumber)
        );

        Accounts accounts = accountsRepository.findAccountsByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("account","customer",customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoanDto> loansDtoResponseEntity = loansFeignClient.findLoan(correlationID, mobileNumber);
        if(null != loansDtoResponseEntity) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.getCard(correlationID, mobileNumber);
        if(null != cardsDtoResponseEntity) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }
        return customerDetailsDto;
    }
}
