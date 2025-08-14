package co.Nitish.paymentSystem.repository;

import co.Nitish.paymentSystem.model.UpiTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpiTransactionRepository extends JpaRepository<UpiTransaction,Long> {
    @Query("SELECT u FROM UpiTransaction u WHERE u.fromUpiId = :upiId AND u.status = 'DEBITED'")
    List<UpiTransaction> findDebitedTransactionsByUpiId(@Param("upiId") String upiId);
}
