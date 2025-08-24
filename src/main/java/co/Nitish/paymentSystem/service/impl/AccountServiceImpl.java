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

            // Mask account number (show only last 4 digits)
            String maskedAccountNumber = "****-****-****-" +
                    account.getAccountNumber().substring(account.getAccountNumber().length() - 4);

            // Mask balance (show as asterisks)
            String maskedBalance = "********";

            // Prepare UPI information
            String upiInfo = account.getUpiId() != null ?
                    "• UPI ID: " + account.getUpiId() + "\n" :
                    "• UPI ID: Not configured yet\n";

            // Send email with PDF attachment
            String subject = "Welcome to Payment System - Account Created Successfully";
            String text = "Dear " + account.getAccountHolderName() + ",\n\n" +
                    "Your account has been successfully created with the following details:\n\n" +
                    "Account Overview:\n" +
                    "• Account Holder: " + account.getAccountHolderName() + "\n" +
                    "• Account Number: " + maskedAccountNumber + "\n" +
                    "• Account Balance: " + maskedBalance + "\n" +
                    upiInfo +
                    "\n" +
                    "For security reasons, sensitive information has been masked in this email.\n" +
                    "Please find your complete account details in the attached PDF document.\n\n" +
                    "Important:\n" +
                    "• Keep your account details secure\n" +
                    "• Do not share your PDF statement with anyone\n" +
                    "• Use your UPI ID for quick payments\n\n" +
                    "⚠️  This is an auto-generated email. Please do not reply to this message.\n\n" +
                    "Best regards,\nCo.Nitish Payment System Team\n" +
                    "Security First | Privacy Protected";

            emailService.sendEmailWithAttachment(userEmail, subject, text, pdfPath);

            // Optional: Send HTML email without attachment
            sendHtmlWelcomeEmail(account, userEmail);

        } catch (Exception e) {
            // Log the error but don't fail the account creation
            System.err.println("Failed to send email: " + e.getMessage());
            // You might want to add proper logging here
        }
    }

    private void sendHtmlWelcomeEmail(Account account, String userEmail) {
        try {
            String maskedAccountNumber = "****-****-****-" +
                    account.getAccountNumber().substring(account.getAccountNumber().length() - 4);

            String upiInfo = account.getUpiId() != null ?
                    "<li><strong>UPI ID:</strong> " + account.getUpiId() + "</li>" :
                    "<li><strong>UPI ID:</strong> Not configured yet</li>";

            Context context = new Context();
            context.setVariable("accountHolderName", account.getAccountHolderName());
            context.setVariable("maskedAccountNumber", maskedAccountNumber);
            context.setVariable("upiInfo", upiInfo);

            emailService.sendHtmlEmail(userEmail,
                    "Welcome to Our Payment System",
                    "welcome-email-template",
                    context
            );
        } catch (Exception e) {
            System.err.println("Failed to send HTML email: " + e.getMessage());
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