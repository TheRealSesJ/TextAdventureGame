package com.sesj.Exceptions;

public class MovementOutOfRangeException extends Exception{
    public MovementOutOfRangeException(){
        super("the target tile is too far away for the players current movement capabilties");
    }
}
