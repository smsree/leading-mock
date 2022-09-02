package com.axisbank.service_application_customer_loan.exception;

public class BusinessLoanClientException extends RuntimeException {
    String message;
    public BusinessLoanClientException(String message){
        super(message);
        this.message=message;
    }
}
