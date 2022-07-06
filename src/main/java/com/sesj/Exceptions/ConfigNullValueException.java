package com.sesj.Exceptions;

public class ConfigNullValueException extends ConfigException{
    public ConfigNullValueException(){
        super("Config.json contains a null value");
    }
}

