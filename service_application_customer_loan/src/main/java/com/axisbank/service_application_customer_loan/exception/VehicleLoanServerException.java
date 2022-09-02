package com.axisbank.service_application_customer_loan.exception;

public class VehicleLoanServerException extends RuntimeException{
    String message;
    public VehicleLoanServerException(String message){
        super(message);
        this.message=message;
    }
}
