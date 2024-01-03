package com.example.exception;

public class NotSuccessfulException extends Exception {
    public NotSuccessfulException(){}
    public NotSuccessfulException(String message){
        super(message);
    }
}
