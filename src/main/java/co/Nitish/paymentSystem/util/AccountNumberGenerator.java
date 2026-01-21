package co.Nitish.paymentSystem.util;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class AccountNumberGenerator {

    private static final String BANK_CODE = "NK"; // Your bank code
    private static final Random random = new Random();

    /**
     * Generates a 14-digit unique account number
     * Format: [BankCode(2)][Year(2)][Month(2)][Random(8)]
     * Example: NK24051234567890
     */
    public String generateAccountNumber() {
        LocalDateTime now = LocalDateTime.now();

        // Format: BankCode + Year + Month + Random 8 digits
        String year = String.format("%02d", now.getYear() % 100); // Last 2 digits of year
        String month = String.format("%02d", now.getMonthValue());

        // Generate 8 random digits
        String randomDigits = String.format("%08d", random.nextInt(100000000));

        return BANK_CODE + year + month + randomDigits;
    }

    /**
     * Generates UPI ID from phone number
     * Format: phoneNumber@nkco
     */
    public String generateUpiId(String phoneNumber) {
        // Remove any non-digit characters from phone number
        String cleanPhone = phoneNumber.replaceAll("[^\\d]", "");
        return cleanPhone + "@nkco";
    }

    /**
     * Generates a unique card number (16 digits)
     * Format: 4[Random 15 digits] (starting with 4 for Visa)
     */
    public String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder("4"); // Start with 4 for Visa

        // Generate 15 random digits
        for (int i = 0; i < 15; i++) {
            cardNumber.append(random.nextInt(10));
        }

        // Format with spaces: XXXX XXXX XXXX XXXX
        String formatted = cardNumber.toString();
        return formatted.replaceAll("(.{4})", "$1 ").trim();
    }

    /**
     * Generates card expiry date (5 years from now)
     * Format: MM/YY
     */
    public String generateCardExpiry() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plusYears(5);

        String month = String.format("%02d", expiryDate.getMonthValue());
        String year = String.format("%02d", expiryDate.getYear() % 100);

        return month + "/" + year;
    }

    /**
     * Generates 3-digit CVV
     */
    public String generateCvv() {
        return String.format("%03d", random.nextInt(1000));
    }

    /**
     * Validates Luhn algorithm for card numbers
     */
    public boolean isValidCardNumber(String cardNumber) {
        String cleanNumber = cardNumber.replaceAll("[^\\d]", "");

        if (cleanNumber.length() != 16) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;

        for (int i = cleanNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cleanNumber.substring(i, i + 1));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}