package co.Nitish.paymentSystem.dto;

import lombok.*;

@Data@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class CashPaymentRequestDto {
    private  String accountHolderName;
    private  String fromAccount;
    private String toAccount;
    private double amount;
    private String remark;
}
