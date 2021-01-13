package com.printfactura.core.error;

public class ApiRetryException extends RuntimeException {

    public ApiRetryException(String message) {
        super(message);
    }
}
