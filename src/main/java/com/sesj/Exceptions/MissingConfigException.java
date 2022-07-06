package com.sesj.Exceptions;

public class MissingConfigException extends ConfigException{
    public MissingConfigException(){
        super("Config.json not found");
    }
}
