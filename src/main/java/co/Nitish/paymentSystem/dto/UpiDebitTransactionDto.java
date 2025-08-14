package co.Nitish.paymentSystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter@NoArgsConstructor@AllArgsConstructor
public class UpiDebitTransactionDto {
    private String receiverUpiId;
    private double amount;
    private String status;
    private String remark;
    private LocalDateTime transactionTime;

}
