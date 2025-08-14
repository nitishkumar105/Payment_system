package co.Nitish.paymentSystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class UpiTransactionDto {

    private String upiHolderName;
    private String fromUpiId;
    private String  toUpiId;
    private double amount;
    private String remark;
    private String status;
    private LocalDateTime transactionTime;

}
