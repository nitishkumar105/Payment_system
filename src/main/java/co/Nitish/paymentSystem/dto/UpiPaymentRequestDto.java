package co.Nitish.paymentSystem.dto;

import lombok.*;

@Data@NoArgsConstructor@AllArgsConstructor@Getter@Setter
public class UpiPaymentRequestDto {
       private String fromUpiId;
       private String toAUpiId;
       private String remark;
       private double amount;
}
