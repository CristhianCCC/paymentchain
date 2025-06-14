package com.paymentchain.product.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends Exception{
    private long id;
    private String code;
    private HttpStatus httpStatus;

    public BusinessRuleException(String code, HttpStatus httpStatus, long id, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
        this.id = id;
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
