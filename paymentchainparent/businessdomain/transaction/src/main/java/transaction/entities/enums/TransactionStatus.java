package transaction.entities.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;

//creando la clase transactionstatus que contendra los codigos de estados
public enum TransactionStatus {

    PENDIENTE ("1"),

    LIQUIDADA("2"),

    RECHAZADA("3"),

    CANCELADA("4");

    private final String code;

    TransactionStatus(String code){
        this.code = code;
    }

    @JsonIgnore
    public String code(){
        return code;
    }

}
