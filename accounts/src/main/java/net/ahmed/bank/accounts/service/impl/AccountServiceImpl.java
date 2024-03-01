package net.ahmed.bank.accounts.service.impl;

import net.ahmed.bank.accounts.constants.AccountsConstants;
import net.ahmed.bank.accounts.dto.AccountsDto;
import net.ahmed.bank.accounts.dto.CustomerDto;
import net.ahmed.bank.accounts.entity.Accounts;
import net.ahmed.bank.accounts.entity.Customer;
import net.ahmed.bank.accounts.exception.CustomerAlreadyExistsException;
import net.ahmed.bank.accounts.exception.ResourceNotFoundException;
import net.ahmed.bank.accounts.mapper.AccountsMapper;
import net.ahmed.bank.accounts.mapper.CustomerMapper;
import net.ahmed.bank.accounts.repository.AccountsRepository;
import net.ahmed.bank.accounts.repository.CustomerRepository;
import net.ahmed.bank.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountsService  {
    AccountsRepository accountsRepository;
    CustomerRepository customerRepository;

    public AccountServiceImpl(AccountsRepository accountsRepository, CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto)  {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optCustomer = customerRepository.findCustomerByMobileNumber(customer.getMobileNumber());
        if(optCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("phone number related to other customer "+customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer){
        Accounts accounts = new Accounts();
        accounts.setAccountNumber(10000L+new Random().nextInt(90000));
        accounts.setCustomerId(customer.getCustomerId());
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADRESS);
        return accounts;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("customer","Phone Number",mobileNumber)
        );
        Accounts accounts = accountsRepository.findAccountsByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("account","customer",customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        Accounts accounts = new Accounts();
        if (accountsDto != null ){
             accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("account","account number",accountsDto.getAccountNumber().toString())
            );
        }
        AccountsMapper.mapToAccounts(accountsDto, accounts);
        accounts = accountsRepository.save(accounts);
        Long customerId = accounts.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()-> new ResourceNotFoundException("customer","customer id",customerId.toString())
        );
        CustomerMapper.mapToCustomer(customerDto,customer);
        customerRepository.save(customer);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * @param phoneNumber
     * @return
     */
    @Override
    public boolean deleteAccount(String phoneNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(phoneNumber).orElseThrow(
                ()-> new ResourceNotFoundException("customer","phone number", phoneNumber )
        );
        Accounts accounts = accountsRepository.findAccountsByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("customer","customer id", customer.getCustomerId().toString())
        );
        customerRepository.delete(customer);
        accountsRepository.delete(accounts);

        return true;
    }


}
