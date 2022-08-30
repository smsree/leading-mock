package com.axisbank.loanappofferservicewebclient.exception;

public class LoanClientException extends RuntimeException{
    private String message;
    public LoanClientException(String message){
        super(message);
        this.message=message;
    }
}
