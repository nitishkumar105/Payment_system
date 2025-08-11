package co.Nitish.paymentSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private  String accountHolderName;
     private  String accountNumber;
     private double balance;

     // for card and upi
    private String upiId;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;



}
