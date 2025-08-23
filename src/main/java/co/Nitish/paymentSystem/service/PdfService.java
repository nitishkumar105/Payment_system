package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.model.Account;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    @Value("${app.pdf.storage-path:./pdf-storage/}")
    private String pdfStoragePath;

    public String generateAccountPdf(Account account, String email) {
        try {
            // Create directory if it doesn't exist
            File directory = new File(pdfStoragePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "account_" + account.getAccountNumber() + "_" +
                    System.currentTimeMillis() + ".pdf";
            String filePath = pdfStoragePath + fileName;

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Account Registration Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Add account details table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20);

            addTableHeader(table);
            addAccountDetails(table, account);

            document.add(table);

            // Add footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
            Paragraph footer = new Paragraph(
                    "Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                            "\nThis is an auto-generated document. Please do not reply to this email.",
                    footerFont
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();
            return filePath;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

        PdfPCell headerCell = new PdfPCell(new Phrase("Account Information", headerFont));
        headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
        headerCell.setColspan(2);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setPadding(10);
        table.addCell(headerCell);
    }

    private void addAccountDetails(PdfPTable table, Account account) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        addTableRow(table, "Account Holder", account.getAccountHolderName(), labelFont, valueFont);
        addTableRow(table, "Account Number", account.getAccountNumber(), labelFont, valueFont);
        addTableRow(table, "Balance", "₹" + account.getBalance(), labelFont, valueFont);
        addTableRow(table, "UPI ID", account.getUpiId() != null ? account.getUpiId() : "Not set", labelFont, valueFont);
        addTableRow(table, "Card Number", account.getCardNumber() != null ? maskCardNumber(account.getCardNumber()) : "Not set", labelFont, valueFont);
        addTableRow(table, "Account Created", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), labelFont, valueFont);
    }

    private void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(5);
        labelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setPadding(5);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 12) {
            return cardNumber;
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}