package co.Nitish.paymentSystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolderName;

    @Column(unique = true, length = 20)
    private String accountNumber;  // Will be auto-generated 14+ digit

   //  @Column(precision = 15, scale = 2)
    private double balance = 0.0;  // Default to 0 during creation

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String upiId;  // Will be auto-generated as phoneNumber@nkco

    @Column(unique = true)
    private String cardNumber;  // Will be auto-generated

    private String cardExpiry;  // Will be auto-generated as 5 years from creation

    private String cardCvv;  // Will be auto-generated

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}