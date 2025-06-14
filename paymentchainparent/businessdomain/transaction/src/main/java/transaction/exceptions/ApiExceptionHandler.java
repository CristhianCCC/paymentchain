package transaction.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import transaction.common.StandarizedApiExceptionResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<?> transactionHandleError (BusinessRuleException ex){
        StandarizedApiExceptionResponse standarizedApiExceptionResponse = new StandarizedApiExceptionResponse(ex.getCode(), ex.getMessage(), "El valor no puede ser menor a 0", "saldo insuficiente para realizar esta operacion", "transaction error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standarizedApiExceptionResponse);
    }
}
