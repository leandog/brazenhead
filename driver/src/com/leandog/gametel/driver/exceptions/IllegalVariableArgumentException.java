package com.leandog.gametel.driver.exceptions;

public class IllegalVariableArgumentException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public IllegalVariableArgumentException() {
        super("Variable names must be in the form of '@@some_name@@'.");
    }

}
