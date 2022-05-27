package com.example.demo.exceptions;

public class AmountOutOfStorageException extends IllegalArgumentException{

    public AmountOutOfStorageException(String cause) {
        super(cause);
    }

    public AmountOutOfStorageException() {
        super("Books amount less then the specified amount");
    }
}
