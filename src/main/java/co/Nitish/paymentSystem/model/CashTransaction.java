package co.Nitish.paymentSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class CashTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String accountHolderName;
    private  String fromAccount;
    private String toAccount;
    private double balance;
    private String remark;
    private LocalDateTime transactionTime;

}
