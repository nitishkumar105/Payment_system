package co.Nitish.paymentSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

 @AllArgsConstructor@NoArgsConstructor@Getter@Setter
@Entity
public class UpiTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String upiHolderName;
     private String fromUpiId;
     private String  toUpiId;
     private double amount;
     private String remark;
     private LocalDateTime transactionTime;

    public UpiTransaction(Object o, String accountHolderName, String upiId, String upiId1, double amount, String paymentToSwiggy, LocalDateTime now) {
    }
}
