package net.ahmed.bank.accounts.service;

import net.ahmed.bank.accounts.dto.CustomerDetailsDto;

public interface ICustomerDetailsService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
