package net.ahmed.bank.accounts.service;

import net.ahmed.bank.accounts.dto.AccountsDto;
import net.ahmed.bank.accounts.dto.CustomerDto;
import net.ahmed.bank.accounts.dto.ResponseDto;
import net.ahmed.bank.accounts.entity.Accounts;

public interface IAccountsService {

    /**
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String phoneNumber);
}
