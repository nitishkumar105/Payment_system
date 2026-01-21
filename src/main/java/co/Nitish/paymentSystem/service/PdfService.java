package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.model.Account;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
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

    // Colors for PDF
    private static final BaseColor HEADER_COLOR = new BaseColor(41, 128, 185);  // Blue
    private static final BaseColor LIGHT_BLUE = new BaseColor(236, 240, 241);   // Light gray-blue
    private static final BaseColor DARK_GRAY = new BaseColor(52, 73, 94);       // Dark gray
    private static final BaseColor SUCCESS_GREEN = new BaseColor(46, 204, 113); // Green

    /**
     * Generate professional account statement PDF
     */
    public String generateAccountStatementPdf(Account account) {
        try {
            // Create directory if it doesn't exist
            File directory = new File(pdfStoragePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "Account_Statement_" + account.getAccountNumber() + "_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
            String filePath = pdfStoragePath + fileName;

            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Add header/footer
            writer.setPageEvent(new PdfHeaderFooter());

            document.open();

            // Add bank logo/header
            addBankHeader(document);

            // Add account details section
            addAccountDetailsSection(document, account);

            // Add card details section
            addCardDetailsSection(document, account);

            // Add terms and conditions
            addTermsAndConditions(document);

            // Add footer
            addFooter(document);

            document.close();

            System.out.println("PDF generated successfully: " + filePath);
            return filePath;

        } catch (Exception e) {
            System.err.println("Failed to generate PDF: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    /**
     * Generate transaction receipt PDF
     */
    public String generateTransactionReceiptPdf(String transactionId, Account account,
                                                double amount, String transactionType) {
        try {
            File directory = new File(pdfStoragePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "Receipt_" + transactionId + ".pdf";
            String filePath = pdfStoragePath + fileName;

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            addTransactionHeader(document, transactionId);
            addTransactionDetails(document, account, amount, transactionType);
            addTransactionFooter(document);

            document.close();

            return filePath;

        } catch (Exception e) {
            throw new RuntimeException("Transaction receipt generation failed", e);
        }
    }

    private void addBankHeader(Document document) throws DocumentException {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.WHITE);
        Paragraph header = new Paragraph("NITISH  CORPORATION ", headerFont);
        header.setAlignment(Element.ALIGN_CENTER);
        header.setSpacingAfter(10);

        // Create colored rectangle background
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell(header);
        cell.setBackgroundColor(HEADER_COLOR);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(15);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        document.add(table);

        // Add subtitle
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, DARK_GRAY);
        Paragraph subtitle = new Paragraph("Account Opening Confirmation", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(20);
        document.add(subtitle);
    }

    private void addAccountDetailsSection(Document document, Account account) throws DocumentException {
        // Section header
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, DARK_GRAY);
        Paragraph sectionHeader = new Paragraph("Account Information", sectionFont);
        sectionHeader.setSpacingBefore(15);
        sectionHeader.setSpacingAfter(10);
        document.add(sectionHeader);

        // Create table for account details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(15);

        addTableRow(table, "Account Holder", account.getAccountHolderName(), true);
        addTableRow(table, "Account Number", account.getAccountNumber(), false);
        addTableRow(table, "Account Type", "Savings Account", true);
        addTableRow(table, "Phone Number", account.getPhoneNumber(), false);
        addTableRow(table, "Email Address", account.getEmail(), true);
        addTableRow(table, "UPI ID", account.getUpiId(), false);
        addTableRow(table, "Account Created", account.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")), true);
        addTableRow(table, "Initial Balance", String.format("₹%.2f", account.getBalance()), false);

        document.add(table);
    }

    private void addCardDetailsSection(Document document, Account account) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, DARK_GRAY);
        Paragraph sectionHeader = new Paragraph("Debit Card Details", sectionFont);
        sectionHeader.setSpacingBefore(15);
        sectionHeader.setSpacingAfter(10);
        document.add(sectionHeader);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(15);

        addTableRow(table, "Card Number", "**** **** **** " +
                account.getCardNumber().replaceAll("\\s+", "").substring(12), true);
        addTableRow(table, "Card Type", "VISA Debit", false);
        addTableRow(table, "Valid Through", account.getCardExpiry(), true);
        addTableRow(table, "Card Status", "Active", false);
        addTableRow(table, "Daily Limit", "₹50,000", true);

        document.add(table);

        // Add security warning
        Font warningFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.RED);
        Paragraph warning = new Paragraph("⚠️ For security reasons, CVV is not displayed in this document.", warningFont);
        warning.setSpacingBefore(10);
        document.add(warning);
    }

    private void addTableRow(PdfPTable table, String label, String value, boolean isAlternate) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, DARK_GRAY);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11, DARK_GRAY);

        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(8);
        labelCell.setBorder(Rectangle.BOTTOM);
        labelCell.setBorderColor(BaseColor.LIGHT_GRAY);
        if (isAlternate) {
            labelCell.setBackgroundColor(LIGHT_BLUE);
        }

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setPadding(8);
        valueCell.setBorder(Rectangle.BOTTOM);
        valueCell.setBorderColor(BaseColor.LIGHT_GRAY);
        if (isAlternate) {
            valueCell.setBackgroundColor(LIGHT_BLUE);
        }

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addTermsAndConditions(Document document) throws DocumentException {
        Font termsFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, DARK_GRAY);
        Paragraph termsHeader = new Paragraph("Terms and Conditions", termsFont);
        termsHeader.setSpacingBefore(20);
        termsHeader.setSpacingAfter(10);
        document.add(termsHeader);

        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10, DARK_GRAY);
        String terms = """
            1. This document is computer generated and does not require a signature.
            2. Please keep your account details confidential.
            3. Report lost/stolen cards immediately.
            4. Transactions are subject to bank terms and conditions.
            5. This statement is valid for official purposes.
            """;

        Paragraph termsContent = new Paragraph(terms, contentFont);
        termsContent.setSpacingAfter(15);
        document.add(termsContent);
    }

    private void addFooter(Document document) throws DocumentException {
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.GRAY);

        Paragraph footer = new Paragraph();
        footer.add(new Chunk("NK Bank Ltd. | Registered Office: Delhi, India | Contact: 1800-123-4567", footerFont));
        footer.add(Chunk.NEWLINE);
        footer.add(new Chunk("Email: support@nkbank.com | Website: www.nkbank.com", footerFont));
        footer.add(Chunk.NEWLINE);
        footer.add(new Chunk("Generated on: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")), footerFont));

        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20);

        document.add(footer);
    }

    private void addTransactionHeader(Document document, String transactionId) throws DocumentException {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, HEADER_COLOR);
        Paragraph header = new Paragraph("TRANSACTION RECEIPT", headerFont);
        header.setAlignment(Element.ALIGN_CENTER);
        header.setSpacingAfter(10);
        document.add(header);

        Font idFont = FontFactory.getFont(FontFactory.HELVETICA, 12, DARK_GRAY);
        Paragraph id = new Paragraph("Transaction ID: " + transactionId, idFont);
        id.setAlignment(Element.ALIGN_CENTER);
        id.setSpacingAfter(20);
        document.add(id);
    }

    private void addTransactionDetails(Document document, Account account, double amount, String transactionType)
            throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(20);

        addTransactionRow(table, "Date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
        addTransactionRow(table, "Time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        addTransactionRow(table, "Account Holder", account.getAccountHolderName());
        addTransactionRow(table, "Account Number", maskAccountNumber(account.getAccountNumber()));
        addTransactionRow(table, "Transaction Type", transactionType);
        addTransactionRow(table, "Amount", String.format("₹%.2f", amount));
        addTransactionRow(table, "Status", "COMPLETED");

        document.add(table);
    }

    private void addTransactionRow(PdfPTable table, String label, String value) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(8);
        labelCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setPadding(8);
        valueCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addTransactionFooter(Document document) throws DocumentException {
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY);
        Paragraph footer = new Paragraph("Thank you for banking with us!", footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(30);
        document.add(footer);
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 8) {
            return accountNumber;
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}

/**
 * PDF Header/Footer Event Handler
 */
class PdfHeaderFooter extends PdfPageEventHelper {
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            PdfPTable footer = new PdfPTable(1);
            footer.setTotalWidth(500);
            footer.setLockedWidth(true);
            footer.setHorizontalAlignment(Element.ALIGN_CENTER);

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY);
            Phrase phrase = new Phrase("Page " + writer.getPageNumber() + " | Confidential Document", font);

            PdfPCell cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.addCell(cell);

            footer.writeSelectedRows(0, -1,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, writer.getDirectContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}