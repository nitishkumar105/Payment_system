package co.Nitish.paymentSystem.dto;

import lombok.*;

@Data
@NoArgsConstructor@AllArgsConstructor@Getter@Setter
public class AccountDto {
     private String accountHolderName;
     private String accountNumber;
     private double amount;

     // card and upiId details
    private String upiId;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;
}
