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
    private LocalDateTime transactionTime;

    public UpiTransactionDto(Object o, String accountHolderName, String upiId, String upiId1, double amount, String paymentToSwiggy, LocalDateTime now) {
    }
}
