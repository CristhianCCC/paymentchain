package transaction.entities.enums;

//creando la clase transactionchannel que contendra los codigos de estados

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum TransactionChannel {

    WEB("1"),

    CAJERO("2"),

    OFICINA("3");

    private final String code;

     TransactionChannel (String code){
        this.code = code;
    }

    @JsonIgnore
    public String code(){
         return code;
    }

}
