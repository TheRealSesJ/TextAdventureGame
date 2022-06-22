package com.sesj.Exceptions;

public class MissingParameterException extends Exception{
    public MissingParameterException(){
        super("a parameter for the intended command is unspecified");
    }
}
