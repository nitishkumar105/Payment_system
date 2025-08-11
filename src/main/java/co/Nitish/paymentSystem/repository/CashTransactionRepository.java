package co.Nitish.paymentSystem.repository;

import co.Nitish.paymentSystem.model.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashTransactionRepository extends JpaRepository<CashTransaction,Long> {
}
