package com.example.demo.exceptions;

public class BookNotFoundException extends IllegalArgumentException{
    public BookNotFoundException() {
        super("Book with the specified index not found");
    }

    public BookNotFoundException(String s) {
        super(s);
    }
}
