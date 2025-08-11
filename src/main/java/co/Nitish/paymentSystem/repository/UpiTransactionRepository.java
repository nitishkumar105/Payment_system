package co.Nitish.paymentSystem.repository;

import co.Nitish.paymentSystem.model.UpiTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpiTransactionRepository extends JpaRepository<UpiTransaction,Long> {
}
