package com.leandog.gametel.driver.commands;

import java.util.Arrays;

import com.leandog.gametel.util.Objects;

public class Command {
    
    private final String name;
    private final Object[] arguments;
    private Target target;
    
    public enum Target {
        LastResultOrRobotium,
        Robotium,
    }
    
    public Command() {
        name = null;
        arguments = new Object[0];
        target = Target.LastResultOrRobotium;
    }

    public Command(final String name) {
        this.name = name;
        arguments = new Object[0];
        target = Target.LastResultOrRobotium;
    }

    public Command(final String name, final Object... arguments) {
        this.name = name;
        this.arguments = arguments;
        target = Target.LastResultOrRobotium;
    }

    public Command(final String name, final Target target,  final Object... arguments) {
        this.name = name;
        this.arguments = arguments;
        this.target = Target.LastResultOrRobotium;
        if( target != null ) {
            this.target = target;
        }
    }

    public String getName() {
        return name;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Target getTarget() {
        return target;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() + Arrays.hashCode(arguments);
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