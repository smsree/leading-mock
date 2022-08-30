package com.axisbank.loanappofferservicewebclient.exception;

public class LoanServerException extends RuntimeException{
    private String message;
    public LoanServerException(String message){
        super(message);
        this.message=message;
    }
}
