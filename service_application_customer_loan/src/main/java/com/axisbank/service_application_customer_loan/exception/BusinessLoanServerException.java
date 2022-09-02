package com.axisbank.service_application_customer_loan.exception;

public class BusinessLoanServerException extends RuntimeException{
    String message;
    public BusinessLoanServerException(String message){
        super(message);
        this.message=message;
    }
}
