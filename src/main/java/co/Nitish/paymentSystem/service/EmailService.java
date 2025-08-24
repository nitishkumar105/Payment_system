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
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("nitishkumaryadav105@gmail.com");

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toEmail);

        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
        }
    }

    public void sendAccountCreationEmail(String toEmail, String accountHolderName,
                                         String accountNumber, double balance) {
        String subject = "Welcome to Payment System - Account Created Successfully";
        String body = "Dear " + accountHolderName + ",\n\n" +
                "Your account has been successfully created!\n\n" +
                "Account Details:\n" +
                "• Account Holder: " + accountHolderName + "\n" +
                "• Account Number: " + accountNumber + "\n" +
                "• Initial Balance: ₹" + balance + "\n\n" +
                "Thank you for choosing our payment system.\n\n" +
                "Best regards,\nPayment System Team";

        sendSimpleEmail(toEmail, subject, body);
    }
}