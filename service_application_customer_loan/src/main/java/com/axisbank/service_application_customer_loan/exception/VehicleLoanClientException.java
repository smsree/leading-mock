package com.axisbank.service_application_customer_loan.exception;

public class VehicleLoanClientException extends RuntimeException{
    String message;
    public VehicleLoanClientException(String message){
        super(message);
        this.message=message;
    }
}
