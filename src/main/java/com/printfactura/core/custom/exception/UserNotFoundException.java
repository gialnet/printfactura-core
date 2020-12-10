package com.printfactura.core.custom.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String UsereMail) {
        super(String.format("User with email %s not found", UsereMail));
    }
}
