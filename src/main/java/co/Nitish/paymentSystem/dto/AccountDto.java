package co.Nitish.paymentSystem.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String accountHolderName;
    private String phoneNumber;
    private String email;

    // These fields will be auto-generated, so no need in DTO
    // private String accountNumber;  // Auto-generated
    // private double balance;        // Will be 0.0
    // private String upiId;         // Auto-generated from phone
    // private String cardNumber;    // Auto-generated
    // private String cardExpiry;    // Auto-generated
    // private String cardCvv;       // Auto-generated
}