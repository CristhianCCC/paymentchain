package transaction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import transaction.entities.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.accountIban = ?1")
    List<Transaction> findByIbanNumber(String ibanNumber);

}
