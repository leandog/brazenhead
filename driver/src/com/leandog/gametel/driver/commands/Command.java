package com.leandog.gametel.driver.commands;

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
}