package com.sesj.Exceptions;

public class NotTraversableException extends Exception{
    public NotTraversableException(){
        super("the target tile is not traversable");
    }
}
