package com.sesj.Exceptions;

public class MovementOutOfBoundsException extends Exception{
    public MovementOutOfBoundsException(){
        super("the target tile does not exist");
    }
}
