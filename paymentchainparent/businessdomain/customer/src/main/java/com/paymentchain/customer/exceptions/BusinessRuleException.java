package com.paymentchain.customer.exceptions;


import org.springframework.http.HttpStatus;


//excepciones para el negocio

public class BusinessRuleException extends Exception{

    //se añaden los atributos que se consideren necesarios  y esta clase extiende de exception
    private long id;
    private String code;
    private HttpStatus httpStatus;

    /*se crean los constructores personalizados que contienen la informacion necesaria, se pueden crear tantos como sea requerido, adicionalmente
    estos constructores pueden ser utilizados al mandar a llamar esta clase en el service, al se llamada la clase se puede escoger que constructor
    se desea usar junto con los parametros para personalizarlos desde ahi*/
    public BusinessRuleException(String code, HttpStatus httpStatus, long id, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
        this.id = id;
    }

    public BusinessRuleException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
