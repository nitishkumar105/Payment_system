package co.Nitish.paymentSystem.dto;

import lombok.*;

@Data@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class CardPaymentRequestDto {
     private String fromCardNumber;
     private String cardExpiry;
     private String cardCvv;
     private String toCardNumber;
     private String remark;
     private double amount;
}
