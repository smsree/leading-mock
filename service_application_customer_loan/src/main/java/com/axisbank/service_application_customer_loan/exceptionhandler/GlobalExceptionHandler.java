package com.axisbank.service_application_customer_loan.exceptionhandler;

import com.axisbank.service_application_customer_loan.exception.CustomerClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerClientException.class)
    public ResponseEntity<String> customerClientException4XX(CustomerClientException ex){
        log.error("Exception caught customerClientException4XX:{}",ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleClientServerException(RuntimeException ex){
        log.error("Exception handle in server exception:{}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
