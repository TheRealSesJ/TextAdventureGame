package com.sesj.Exceptions;

public class ConfigException extends Exception{
    public ConfigException(){
        super("Config.json error");
    }

    public ConfigException(String message){
        super(message);
    }
}
