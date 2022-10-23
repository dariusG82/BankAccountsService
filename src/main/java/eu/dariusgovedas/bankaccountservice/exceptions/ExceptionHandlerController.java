package eu.dariusgovedas.bankaccountservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BankAccountNotFoundException exception){

        ErrorResponse error = new ErrorResponse();

        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception){

        ErrorResponse error = new ErrorResponse();

        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setErrorMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}