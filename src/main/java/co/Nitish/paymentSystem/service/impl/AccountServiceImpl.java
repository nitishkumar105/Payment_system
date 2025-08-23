package co.Nitish.paymentSystem.service.impl;

import co.Nitish.paymentSystem.customExceptionClass.AccountNotFoundException;
import co.Nitish.paymentSystem.dto.AccountDto;
import co.Nitish.paymentSystem.dto.AccountInfoDto;
import co.Nitish.paymentSystem.mapper.AccountMapper;
import co.Nitish.paymentSystem.model.Account;
import co.Nitish.paymentSystem.repository.AccountRepository;
import co.Nitish.paymentSystem.service.AccountService;
import co.Nitish.paymentSystem.service.EmailService;
import co.Nitish.paymentSystem.service.PdfService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final PdfService pdfService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              EmailService emailService,
                              PdfService pdfService) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.pdfService = pdfService;
    }

    @Override
    public AccountInfoDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.AccountDtoToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);

        // Send email asynchronously
        String userEmail=accountDto.getEmail();
        sendAccountCreationEmail(savedAccount, userEmail); // Replace with actual email from DTO

        return AccountMapper.AccountToAccountInfoDto(savedAccount);
    }

    @Async
    public void sendAccountCreationEmail(Account account, String userEmail) {
        try {
            // Generate PDF
            String pdfPath = pdfService.generateAccountPdf(account, userEmail);

            // Send email with PDF attachment
            String subject = "Welcome to Payment System - Account Created Successfully";
            String text = "Dear " + account.getAccountHolderName() + ",\n\n" +
                    "Your account has been successfully created.\n" +
                    "Account Number: " + account.getAccountNumber() + "\n" +
                    "Initial Balance: ₹" + account.getBalance() + "\n\n" +
                    "Please find your account details attached.\n\n" +
                    "Best regards,\nPayment System Team";

            emailService.sendEmailWithAttachment(userEmail, subject, text, pdfPath);

            // Optional: Send HTML email without attachment
            sendHtmlWelcomeEmail(account, userEmail);

        } catch (Exception e) {
            // Log the error but don't fail the account creation
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    private void sendHtmlWelcomeEmail(Account account, String userEmail) {
        Context context = new Context();
        context.setVariable("accountHolderName", account.getAccountHolderName());
        context.setVariable("accountNumber", account.getAccountNumber());
        context.setVariable("balance", account.getBalance());
        context.setVariable("upiId", account.getUpiId());

        emailService.sendHtmlEmail(userEmail,
                "Welcome to Our Payment System",
                "welcome-email-template",
                context
        );
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