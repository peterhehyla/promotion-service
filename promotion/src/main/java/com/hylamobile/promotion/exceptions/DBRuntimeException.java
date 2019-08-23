package com.hylamobile.promotion.exceptions;

public class DBRuntimeException extends RuntimeException{
    public DBRuntimeException(){
    }
    public DBRuntimeException(Throwable ex){
        super(ex);
    }
}
