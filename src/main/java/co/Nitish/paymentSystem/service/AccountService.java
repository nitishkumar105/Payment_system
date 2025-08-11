package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    AccountInfoDto createAccount(AccountDto accountDto) ;
}
