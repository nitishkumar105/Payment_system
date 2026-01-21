package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;

import java.util.List;

public interface AccountService {
    AccountInfoDto createAccount(AccountDto accountDto);
    List<AccountInfoDto> getAllAccount();
    AccountInfoDto getAccountByAccountNumber(String accountNumber);
    AccountInfoDto getAccountByPhoneNumber(String phoneNumber);
    AccountInfoDto getAccountByUpiId(String upiId);
}