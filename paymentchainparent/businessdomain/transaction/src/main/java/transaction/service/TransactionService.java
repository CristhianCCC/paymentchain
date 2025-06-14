package transaction.service;

import transaction.entities.Transaction;
import transaction.exceptions.BusinessRuleException;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    public Transaction createTransaction (Transaction transaction) throws BusinessRuleException;

    public Transaction updateTransaction (Long transactionId, Transaction trasactionUpdated);

    public List<Transaction> findAllTransactions ();

    public Optional<Transaction> findTransactionByAccountNumber(Long id);

    public List<Transaction> listTransactionsbyAccountIban(String ibanNumber);

    public Void deleteTransactionById(Long id);


}
