package transaction.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import transaction.Repository.TransactionRepository;
import transaction.entities.Transaction;
import transaction.entities.enums.TransactionStatus;
import transaction.entities.enums.TransactionType;
import transaction.exceptions.ApiExceptionHandler;
import transaction.exceptions.BusinessRuleException;
import transaction.service.TransactionService;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) throws BusinessRuleException {
        double fee = transaction.getFee();
        double amount = transaction.getAmount();
        double total = amount - fee;
        LocalDateTime date = transaction.getDateTime();


        //si la fecha es mayor el estado de la transaccion es pendiente
        if (date.isAfter(LocalDateTime.now())){
            transaction.setStatus(TransactionStatus.PENDIENTE);
        } else {
            transaction.setStatus(transaction.getStatus());
        }

        //si es negativo es un retiro, si es positivo es un abono
        if(amount > 0) {
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setAmount(total);

        //si es negativo se asigna un fee de 0.98
        //se calcula el total en base al fee
        //se setea la transferencia de tipo withdrawal y se setean tanto el fee como el total
        } else {
            fee = 0.98;
            total = amount + fee;
            transaction.setType(TransactionType.WITHDRAWAL);
            transaction.setFee(fee);
            transaction.setAmount(total);
        }

        //el retiro y/o transferencia no puede dejar la cuenta en 0
        //el monto de la transaccion no puede ser 0
        if(total ==0 || amount == 0 ) {
            BusinessRuleException businessRuleException = new BusinessRuleException("1032", HttpStatus.BAD_REQUEST, "la cuenta no puede quedar en 0");
            throw businessRuleException;
        }

        return transactionRepository.save(transaction);
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
