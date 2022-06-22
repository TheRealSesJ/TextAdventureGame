package com.sesj.Exceptions;

public class NotTraversibleException extends Exception{
    public NotTraversibleException(){
        super("the target tile is not traversible");
    }
}
