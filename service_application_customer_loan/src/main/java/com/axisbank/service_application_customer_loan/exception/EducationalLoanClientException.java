package com.axisbank.service_application_customer_loan.exception;

public class EducationalLoanClientException extends RuntimeException{
    String message;
    public EducationalLoanClientException(String message){
        super(message);
        this.message=message;
    }
}
