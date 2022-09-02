package com.axisbank.service_application_customer_loan.exception;

public class EducationalLoanServerException extends RuntimeException{
    String message;
    public EducationalLoanServerException(String message){
        super(message);
        this.message=message;
    }
}
