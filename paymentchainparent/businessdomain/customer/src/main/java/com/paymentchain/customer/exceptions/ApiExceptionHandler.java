package com.paymentchain.customer.exceptions;


import com.paymentchain.customer.common.StandardizedApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError (Exception ex){
        StandardizedApiExceptionResponse standardizedApiExceptionResponse = new StandardizedApiExceptionResponse("1024",
                "Error de acceso" + ex.getMessage(), "/paymentchain/customer/code{id}", "input output error", "Tecnico");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardizedApiExceptionResponse);
    }


    //cuando lanza una excepcion de tipo BusinessRuleException se debe ejecutar este metodo
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<?> handleBusinessRuleException (BusinessRuleException ex){
        //respuesta estandarizada
        StandardizedApiExceptionResponse standardizedApiExceptionResponse = new StandardizedApiExceptionResponse(ex.getCode(), ex.getMessage(), "validacion", "Error de validacion", "Business");
        return ResponseEntity.status(ex.getHttpStatus()).body(standardizedApiExceptionResponse);
    }

}
