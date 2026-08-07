package co.Nitish.paymentSystem.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Send HTML email with PDF attachment
     */
    public void sendHtmlEmailWithAttachment(String toEmail, String subject,
                                            String templateName, Map<String, Object> templateVariables,
                                            String attachmentPath, String attachmentFileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Set email details
            helper.setFrom("nitishkumarwork105@gmail.com", "NITISH CORPORATION");
            helper.setTo(toEmail);
            helper.setSubject(subject);

            // Process HTML template
            Context context = new Context();
            templateVariables.forEach(context::setVariable);
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            // Add PDF attachment
            if (attachmentPath != null && !attachmentPath.trim().isEmpty()) {
                FileSystemResource file = new FileSystemResource(new File(attachmentPath));
                helper.addAttachment(attachmentFileName, file);
            }

            mailSender.send(message);
            System.out.println("HTML email with PDF sent successfully to: " + toEmail);

        } catch (MessagingException e) {
            System.err.println(" Failed to send HTML email to " + toEmail + ": " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(" Unexpected error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Send account creation email with PDF statement
     */
    public void sendAccountCreationEmailWithPdf(String toEmail, String accountHolderName,
                                                String accountNumber, String upiId,
                                                String maskedCardNumber, String cardExpiry,
                                                String pdfFilePath) {
        // Prepare template variables
        Map<String, Object> variables = Map.of(
                "accountHolderName", accountHolderName,
                "accountNumber", accountNumber,
                "maskedAccountNumber", maskAccountNumber(accountNumber),
                "upiId", upiId,
                "maskedCardNumber", maskedCardNumber,
                "cardExpiry", cardExpiry,
                "currentYear", java.time.Year.now().getValue()
        );

        String subject = "Welcome to NITISH CORPORATION - Your Account Details";
        String attachmentName = "Account_Statement_" + accountNumber + ".pdf";

        sendHtmlEmailWithAttachment(
                toEmail,
                subject,
                "account-creation-email",  // Thymeleaf template name
                variables,
                pdfFilePath,
                attachmentName
        );
    }

    /**
     * Send transaction confirmation email with PDF receipt
     */
    public void sendTransactionEmailWithPdf(String toEmail, String accountHolderName,
                                            String transactionId, double amount,
                                            String transactionType, String status,
                                            String pdfFilePath) {
        Map<String, Object> variables = Map.of(
                "accountHolderName", accountHolderName,
                "transactionId", transactionId,
                "amount", String.format("₹%.2f", amount),
                "transactionType", transactionType,
                "status", status,
                "transactionDate", java.time.LocalDate.now().toString(),
                "transactionTime", java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"))
        );

        String subject = "Transaction Confirmation - " + transactionId;
        String attachmentName = "Transaction_Receipt_" + transactionId + ".pdf";

        sendHtmlEmailWithAttachment(
                toEmail,
                subject,
                "transaction-email",  // Thymeleaf template name
                variables,
                pdfFilePath,
                attachmentName
        );
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 8) {
            return accountNumber;
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}