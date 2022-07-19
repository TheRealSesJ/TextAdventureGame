package com.sesj.Exceptions;
/**
 * Exception thrown when Config cannot be loaded
 */
public class MissingConfigException extends ConfigException{
    public MissingConfigException(){
        super("Config.json not found");
    }
}
