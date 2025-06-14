package transaction.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import transaction.entities.enums.TransactionChannel;
import transaction.entities.enums.TransactionStatus;
import transaction.entities.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //identificador de la transaccion
    private String reference;

    private String accountIban;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    private Double amount;

    private Double fee;

    private String description;

    //inyectando transactionstatus de la clase enums
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    //inyectando transactionchannel de la clase enums
    @Enumerated(EnumType.STRING)
    private TransactionChannel channel;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public Transaction(Long id, String reference, String accountIban, Date dateTime, Double amount, Double fee, String description, TransactionStatus status, TransactionChannel channel, TransactionType type) {
        this.id = id;
        this.reference = reference;
        this.accountIban = accountIban;

        this.amount = amount;
        this.fee = fee;
        this.description = description;
        this.status = status;
        this.channel = channel;
        this.type = type;
    }

    public Transaction() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAccountIban() {
        return accountIban;
    }

    public void setAccountIban(String accountIban) {
        this.accountIban = accountIban;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionChannel getChannel() {
        return channel;
    }

    public void setChannel(TransactionChannel channel) {
        this.channel = channel;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
