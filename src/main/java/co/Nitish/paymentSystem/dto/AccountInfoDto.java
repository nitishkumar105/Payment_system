package co.Nitish.paymentSystem.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccountInfoDto {
     private String accountHolderName;
     private String accountNumber;
     private double balance;
     private String phoneNumber;
     private String email;
     private String upiId;
     private String cardNumber;  // Masked
     private String cardExpiry;
     private LocalDateTime createdAt;
}