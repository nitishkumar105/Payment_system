package co.Nitish.paymentSystem.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity@NoArgsConstructor@AllArgsConstructor@Setter@Getter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String roles; // e.g. "ADMIN" or "USER"
    // Link to Account (One-to-One relationship)
    @OneToOne
    @JoinColumn(name = "account_Id")
    private Account account;

    private boolean enabled = true;
    // getters and setters
}

