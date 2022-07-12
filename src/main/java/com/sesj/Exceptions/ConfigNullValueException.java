package com.sesj.Exceptions;

public class ConfigNullValueException extends ConfigException{
    public ConfigNullValueException(){
        super("Config.json contains a null or unreadable value");
    }

    public ConfigNullValueException(String location){
        super("Config.json contains a null or unreadable value at "+location);
    }
}

