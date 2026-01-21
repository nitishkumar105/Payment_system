package co.Nitish.paymentSystem.service.impl;

import co.Nitish.paymentSystem.customExceptionClass.AccountNotFoundException;
import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.mapper.AccountMapper;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.repository.AccountRepository;
import co.Nitish.paymentSystem.service.AccountService;
import co.Nitish.paymentSystem.service.EmailService;
import co.Nitish.paymentSystem.util.AccountNumberGenerator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final AccountNumberGenerator accountNumberGenerator;

    public AccountServiceImpl(AccountRepository accountRepository,
                              EmailService emailService,
                              AccountNumberGenerator accountNumberGenerator) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Override
    @Transactional
    public AccountInfoDto createAccount(AccountDto accountDto) {
        // Check if phone number already exists
        Optional<Account> existingByPhone = accountRepository.findByPhoneNumber(accountDto.getPhoneNumber());
        if (existingByPhone.isPresent()) {
            throw new RuntimeException("Account with phone number " + accountDto.getPhoneNumber() + " already exists");
        }

        // Check if email already exists
        Optional<Account> existingByEmail = accountRepository.findByEmail(accountDto.getEmail());
        if (existingByEmail.isPresent()) {
            throw new RuntimeException("Account with email " + accountDto.getEmail() + " already exists");
        }

        // Create account entity
        Account account = new Account();
        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setPhoneNumber(accountDto.getPhoneNumber());
        account.setEmail(accountDto.getEmail());

        // Auto-generate all fields
       //  account.setAccountNumber(generateUniqueAccountNumber());
        account.setAccountNumber(accountNumberGenerator.generateAccountNumber());
        account.setBalance(0.0); // Default balance is 0
        account.setUpiId(accountNumberGenerator.generateUpiId(accountDto.getPhoneNumber()));
        account.setCardNumber(accountNumberGenerator.generateCardNumber());
        account.setCardExpiry(accountNumberGenerator.generateCardExpiry());
        account.setCardCvv(accountNumberGenerator.generateCvv());

        // Save account
        Account savedAccount = accountRepository.save(account);

        // Send email asynchronously
        sendAccountCreationEmailAsync(savedAccount);

        return AccountMapper.AccountToAccountInfoDto(savedAccount);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        int attempts = 0;
        final int MAX_ATTEMPTS = 10;

        do {
            accountNumber = accountNumberGenerator.generateAccountNumber();
            attempts++;

            if (attempts >= MAX_ATTEMPTS) {
                throw new RuntimeException("Unable to generate unique account number after " + MAX_ATTEMPTS + " attempts");
            }
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }


    @Async
    public void sendAccountCreationEmailAsync(Account account) {
        try {
            String maskedCardNumber = maskCardNumber(account.getCardNumber());

            emailService.sendAccountCreationEmail(
                    account.getEmail(),
                    account.getAccountHolderName(),
                    account.getAccountNumber(),
                    account.getUpiId(),
                    maskedCardNumber,
                    account.getCardExpiry()
            );

        } catch (Exception e) {
            System.err.println("Failed to send account creation email: " + e.getMessage());
            // Don't throw - this is async method
        }
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 8) {
            return accountNumber;
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 16) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
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

    @Override
    public AccountInfoDto getAccountByPhoneNumber(String phoneNumber) {
        Account account = accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return AccountMapper.AccountToAccountInfoDto(account);
    }

    @Override
    public AccountInfoDto getAccountByUpiId(String upiId) {
        Account account = accountRepository.findByUpiId(upiId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return AccountMapper.AccountToAccountInfoDto(account);
    }
}