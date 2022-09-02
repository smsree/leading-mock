package com.axisbank.service_application_customer_loan.exception;

public class CustomerServerException extends RuntimeException{
    String message;
    public CustomerServerException(String message){
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
