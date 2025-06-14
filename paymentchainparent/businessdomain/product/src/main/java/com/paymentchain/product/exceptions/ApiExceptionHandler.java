package com.paymentchain.product.exceptions;

import com.paymentchain.product.common.StandardizedExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<?> handleBusinessRuleException (BusinessRuleException ex){
        //respuesta estandarizada
        StandardizedExceptionResponse standardizedExceptionResponse = new StandardizedExceptionResponse(ex.getCode(), ex.getMessage(), "validacion", "Error de validacion", "Business");
        return ResponseEntity.status(ex.getHttpStatus()).body(standardizedExceptionResponse);
    }

}
