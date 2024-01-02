package com.example.exception;

public class QueryNotFoundException extends Exception{

    public QueryNotFoundException(){}

    public QueryNotFoundException(String message){
        super(message);
    }
}