package com.leandog.gametel.driver.commands;

import java.util.Arrays;

import com.leandog.gametel.util.Objects;

public class Command {
    
    private final String name;
    private final Object[] arguments;
    
    public Command() {
        name = null;
        arguments = new Object[0];
    }

    public Command(final String name) {
        this.name = name;
        arguments = new Object[0];
    }

    public Command(final String name, final Object... arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public Object[] getArguments() {
        return arguments;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(name, arguments);
    }
    
    @Override
    public boolean equals(Object other) {
        if( null == other || !(other instanceof Command)) {
            return false;
        }
        
        final Command otherCommand = (Command)other;
        return Objects.equal(name, otherCommand.getName()) &&
               Arrays.equals(arguments, otherCommand.getArguments());
    }
}