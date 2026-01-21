package co.Nitish.paymentSystem.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nitishkumaryadav105@gmail.com");  // Explicit from address
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + toEmail);

        } catch (Exception e) {
            System.err.println("❌ Failed to send email to " + toEmail + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendAccountCreationEmail(String toEmail, String accountHolderName,
                                         String accountNumber, String upiId,
                                         String maskedCardNumber, String cardExpiry) {
        String subject = "Welcome to NITISH CORPORATION Bank - Account Created Successfully";
        String body = "Dear " + accountHolderName + ",\n\n" +
                "Your bank account has been successfully created with the following details:\n\n" +
                "Account Details:\n" +
                "• Account Holder: " + accountHolderName + "\n" +
                "• Account Number: " + accountNumber + "\n" +
                "• UPI ID: " + upiId + "\n" +
                "• Card Number: " + maskedCardNumber + "\n" +
                "• Card Expiry: " + cardExpiry + "\n" +
                "• Initial Balance: ₹0.00\n\n" +
                "Security Information:\n" +
                "• Your account number is automatically generated and unique\n" +
                "• UPI ID is created from your phone number\n" +
                "• Debit card details are auto-generated\n" +
                "• Initial balance is ₹0.00 - you can add funds anytime\n\n" +
                "Best regards,\nNK Bank Team";

        sendSimpleEmail(toEmail, subject, body);
    }
}