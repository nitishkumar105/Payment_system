package co.Nitish.paymentSystem.repository;

import co.Nitish.paymentSystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByPhoneNumber(String phoneNumber);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByUpiId(String upiId);
    Optional<Account> findByCardNumber(String cardNumber);
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByUpiId(String upiId);
}