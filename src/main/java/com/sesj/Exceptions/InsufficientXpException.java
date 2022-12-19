package com.sesj.Exceptions;

public class InsufficientXpException extends Exception{
    public InsufficientXpException(){
        super("Insufficient xp to complete this action");
    }
}
