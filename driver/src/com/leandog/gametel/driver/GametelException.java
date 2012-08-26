package com.leandog.gametel.driver;

public class GametelException {
    
    public final String exception;
    public final String errorMessage;
    
    public final GametelException theCause;

    public GametelException(final Throwable exception) {
        this.exception = exception.getClass().getName();
        this.errorMessage = exception.getMessage();
        this.theCause = theCause(exception);
    }

    private GametelException theCause(final Throwable exception) {
        Throwable cause = exception.getCause();
        if( null == cause ) {
            return null;
        }
        
        return new GametelException(exception.getCause());
    }
    
}