package transaction.entities.enums;


import com.fasterxml.jackson.annotation.JsonIgnore;

public enum TransactionType {

    WITHDRAWAL("1"),

    DEPOSIT("2");

    private final String code;

    TransactionType (String code){
        this.code = code;
    }

    @JsonIgnore
    public String code(){return code;}

}
