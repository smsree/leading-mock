package com.axisbank.service_application_customer_loan.exception;

public class CustomerClientException extends RuntimeException{
    private String message;
    private Integer statusCode;
    public CustomerClientException(String message,Integer statusCode){
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
