package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    AccountInfoDto createAccount(AccountDto accountDto) ;
      List<AccountInfoDto> getAllAccount();
      AccountInfoDto getAccountByAccountNumber(String accountNumber);
}
