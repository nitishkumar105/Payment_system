package co.Nitish.paymentSystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data@Setter@Getter
public class UpiCreditTransactionDto {
    private String senderUpiId;
    private double amount;
    private String status;
    private String remark;
    private LocalDateTime transactionTime;

    public UpiCreditTransactionDto(String senderUpiId, double amount, String remark, String status, LocalDateTime transactionTime) {
        this.senderUpiId = senderUpiId;
        this.amount = amount;
        this.remark = remark;
        this.status = status;
        this.transactionTime = transactionTime;
    }
}
