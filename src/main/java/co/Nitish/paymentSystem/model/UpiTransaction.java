package co.Nitish.paymentSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

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

}
