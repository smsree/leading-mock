package com.axisbank.service_application_customer_loan.exception;

public class HousingLoanCleintException extends RuntimeException{
    String message;
    public HousingLoanCleintException(String message){
        super(message);
        this.message=message;
    }
}
