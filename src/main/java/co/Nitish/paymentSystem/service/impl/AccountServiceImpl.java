package co.Nitish.paymentSystem.service.impl;

import co.Nitish.paymentSystem.customExceptionClass.AccountNotFoundException;
import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.mapper.AccountMapper;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.repository.AccountRepository;
import co.Nitish.paymentSystem.service.AccountService;
import co.Nitish.paymentSystem.service.EmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final EmailService emailService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              EmailService emailService) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
    }

    @Override
    public AccountInfoDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.AccountDtoToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);

        // Send email asynchronously
        sendAccountCreationEmailAsync(
                accountDto.getEmail(),
                savedAccount.getAccountHolderName(),
                savedAccount.getAccountNumber(),
                savedAccount.getBalance()
        );

        return AccountMapper.AccountToAccountInfoDto(savedAccount);
    }

    @Async
    public void sendAccountCreationEmailAsync(String email, String accountHolderName,
                                              String accountNumber, double balance) {
        if (email != null && !email.trim().isEmpty()) {
            emailService.sendAccountCreationEmail(email, accountHolderName, accountNumber, balance);
        } else {
            System.out.println("No email provided for account: " + accountNumber);
        }
    }

    @Override
    public List<AccountInfoDto> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountMapper::AccountToAccountInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountInfoDto getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return AccountMapper.AccountToAccountInfoDto(account);
    }
}