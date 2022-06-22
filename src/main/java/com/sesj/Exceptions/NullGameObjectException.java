package com.sesj.Exceptions;

public class NullGameObjectException extends Exception{
    public NullGameObjectException(){
        super("there is no object to interact with");
    }
}