package transaction.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transaction.Repository.TransactionRepository;
import transaction.entities.Transaction;
import transaction.entities.enums.TransactionStatus;
import transaction.service.TransactionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Transaction transactionCreated = new Transaction();
        transactionCreated.setReference(transaction.getReference());
        transactionCreated.setAccountIban(transaction.getAccountIban());
        transactionCreated.setDateTime(transaction.getDateTime());
        transactionCreated.setAmount(transaction.getAmount());
        transactionCreated.setFee(transaction.getFee());
        transactionCreated.setDescription(transaction.getDescription());
        transactionCreated.setChannel(transaction.getChannel());
        //si el fee es mayor a 0, se le resta el fee al total de la transaccion
        if (transactionCreated.getFee() > 0) {
           Double calcTotal = transactionCreated.getAmount() - transactionCreated.getFee();
            transactionCreated.setAmount(calcTotal);
        }
        //si la fecha es mayor el estado de la transaccion es pendiente
        if (transactionCreated.getDateTime().isAfter(LocalDateTime.now())){
            transactionCreated.setStatus(TransactionStatus.PENDIENTE);
        } else {
            transactionCreated.setStatus(transaction.getStatus());
        }
        return transactionRepository.save(transactionCreated);
    }

    @Override
    public Transaction updateTransaction(Long transactionId, Transaction transactionUpdated) {
        return transactionRepository.findById(transactionId).map(transactionfound -> {
            transactionfound.setReference(transactionUpdated.getReference());
            transactionfound.setAccountIban(transactionUpdated.getAccountIban());
            transactionfound.setDateTime(transactionUpdated.getDateTime());
            transactionfound.setAmount(transactionUpdated.getAmount());
            transactionfound.setFee(transactionUpdated.getFee());
            transactionfound.setDescription(transactionUpdated.getDescription());
            transactionfound.setStatus(transactionUpdated.getStatus());
            transactionfound.setChannel(transactionUpdated.getChannel());
            //si el fee es mayor a 0, se le resta el fee al total de la transaccion
            if(transactionfound.getFee() > 0){
                Double calcTotal = transactionfound.getAmount() - transactionfound.getFee();
                 transactionfound.setAmount(calcTotal);
            }
            //si la fecha es mayor el estado de la transaccion es pendiente
            if(transactionfound.getDateTime().isAfter(LocalDateTime.now())){
                transactionfound.setStatus(TransactionStatus.PENDIENTE);
            }

            return transactionRepository.save(transactionfound);
        }).orElseThrow(() -> new RuntimeException("Transaccion no encontrada" + transactionId));

    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> findTransactionByAccountNumber(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> listTransactionsbyAccountIban(String ibanNumber) {
        List<Transaction> transactions = transactionRepository.findByIbanNumber(ibanNumber);
        return transactions;

    }

    @Override
    public Void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
        return null;
    }


}
