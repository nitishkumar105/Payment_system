package co.Nitish.paymentSystem.dto;


import lombok.*;

@Data@NoArgsConstructor@AllArgsConstructor@Getter@Setter
public class AccountInfoDto {
     private String accountHolderName;
     private String upiId;
     private String cardNumber;
     private String accountNumber;
     private double amount;

     public AccountInfoDto(String accountHolderName, String accountNumber, String upiId, String cardNumber, double balance, Object o) {
     }
}
