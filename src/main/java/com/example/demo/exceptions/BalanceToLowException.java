package com.example.demo.exceptions;

public class BalanceToLowException extends  IllegalArgumentException{
    public BalanceToLowException(String cause) {
        super(cause);
    }

    public BalanceToLowException() {
        super("Account hasn't got money to purchase the book in the specified amount");
    }
}
