package com.leandog.gametel.driver.commands;

public class Command {
    
    private final String name;
    
    public Command() {
        name = null;
    }

    public Command(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}