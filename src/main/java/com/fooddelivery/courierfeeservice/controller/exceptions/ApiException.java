package com.fooddelivery.courierfeeservice.controller.exceptions;

public class ApiException extends Exception {
    private ApiExceptionErrorType type;

    public ApiException() {
        super();
    }

    public ApiException(String message, ApiExceptionErrorType type) {
        super(message);
        this.type = type;
    }

    public ApiExceptionErrorType getType() {
        return type;
    }

    public void setType(ApiExceptionErrorType type) {
        this.type = type;
    }
}
