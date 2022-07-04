package com.sesj.Exceptions;

public class MissingConfigException extends Exception{
    public MissingConfigException(){
        super("a parameter for the intended command is unspecified");
    }
}
