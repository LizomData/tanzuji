package org.example.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String msg){
        super(msg);
    }
}
