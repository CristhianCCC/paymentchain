package transaction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transaction.entities.Transaction;
import transaction.service.impl.TransactionServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;


    @PostMapping
    public ResponseEntity<Transaction> createTransactions (@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionServiceImpl.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransactions (@PathVariable Long transactionId, @RequestBody Transaction transaction){
        Transaction transactionUpdated = transactionServiceImpl.updateTransaction(transactionId, transaction);
        return ResponseEntity.ok(transactionUpdated);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> listTransactions () {
        List<Transaction> transactionsList = transactionServiceImpl.findAllTransactions();
        return ResponseEntity.ok(transactionsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Transaction>> findTransactionByAccountNumber(@PathVariable Long id){
        Optional<Transaction> transaction = transactionServiceImpl.findTransactionByAccountNumber(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/customer/transactions")
    public ResponseEntity<List<Transaction>> listTransactionsbyAccountIban(@RequestParam String ibanAccount) {
        List<Transaction> transactions = transactionServiceImpl.listTransactionsbyAccountIban(ibanAccount);
        if (transactions == null || transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id){
        transactionServiceImpl.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }
}
