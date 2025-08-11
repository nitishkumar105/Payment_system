package co.Nitish.paymentSystem.repository;

import co.Nitish.paymentSystem.model.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTransactionRepository extends JpaRepository<CardTransaction,Long> {
}
