package org.example.exception;

public class ExpiredJwtException extends Exception{
    public ExpiredJwtException(String msg){
        super(msg);
    }
}
