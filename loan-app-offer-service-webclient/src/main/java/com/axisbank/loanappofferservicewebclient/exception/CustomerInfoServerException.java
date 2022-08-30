package com.axisbank.loanappofferservicewebclient.exception;

public class CustomerInfoServerException extends RuntimeException{


    private String message;

    public CustomerInfoServerException(String message){
        super(message);
        this.message=message;
    }
}
