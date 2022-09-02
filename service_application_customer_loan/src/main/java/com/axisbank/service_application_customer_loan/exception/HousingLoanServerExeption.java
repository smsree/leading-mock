package com.axisbank.service_application_customer_loan.exception;

public class HousingLoanServerExeption extends RuntimeException{
    String message;
    public HousingLoanServerExeption(String message){
        super(message);
        this.message=message;
    }
}
