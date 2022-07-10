package com.sesj.Exceptions;

public class NotScannableException extends Exception{
    public NotScannableException(){
        super("the target tile is not scannable");
    }
}
