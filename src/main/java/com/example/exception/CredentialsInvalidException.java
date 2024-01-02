package com.example.exception;

public class CredentialsInvalidException extends Exception{

    public CredentialsInvalidException(){}

    public CredentialsInvalidException(String message){
        super(message);
    }
}