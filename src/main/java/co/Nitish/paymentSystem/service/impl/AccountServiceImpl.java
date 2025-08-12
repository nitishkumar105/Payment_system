package co.Nitish.paymentSystem.service.impl;

import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.mapper.AccountMapper;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.repository.AccountRepository;
import co.Nitish.paymentSystem.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    public  final AccountRepository accountRepository;
    AccountServiceImpl (AccountRepository accountRepository){
         this.accountRepository=accountRepository;
    }
    @Override
    public AccountInfoDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.AccountDtoToAccount(accountDto);
         Account savedAccount=accountRepository.save(account);
        return AccountMapper.AccountToAccountInfoDto(savedAccount);
    }

    @Override
    public List<AccountInfoDto> getAllAccount() {
         List<Account> accounts=accountRepository.findAll();
          return accounts.stream().map(AccountMapper::AccountToAccountInfoDto).collect(Collectors.toList());
    }
}
