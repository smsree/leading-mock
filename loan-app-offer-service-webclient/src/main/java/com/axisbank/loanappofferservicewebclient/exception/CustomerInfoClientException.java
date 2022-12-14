package com.axisbank.loanappofferservicewebclient.exception;

public class CustomerInfoClientException extends RuntimeException{

    private String message;
    private Integer statusCode;

    public CustomerInfoClientException(String message,Integer statusCode){
        super(message);
        this.message=message;
        this.statusCode=statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
