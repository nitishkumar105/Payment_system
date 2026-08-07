package co.Nitish.paymentSystem.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity@NoArgsConstructor@AllArgsConstructor@Setter@Getter
@Table(name="users")
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

    private String roles ; // Can be "USER", "ADMIN", etc.

    // Link to Account (One-to-One relationship)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

//    @OneToOne
//    @JoinColumn(name = "account_Id")
//    private Account account;

    private boolean enabled = true;
    // getters and setters
}

