package com.leandog.gametel.driver.commands;

import java.util.Arrays;

import com.leandog.gametel.driver.exceptions.IllegalVariableArgumentException;
import com.leandog.gametel.util.Objects;

public class Command {

    private String name;
    private Object[] arguments;
    private Target target;
    private String variable;

    public enum Target {
        LastResultOrRobotium, Robotium,
    }

    public Command() {
        name = null;
        arguments = new Object[0];
        target = Target.LastResultOrRobotium;
    }

    public Command(final String name) {
        this();
        this.name = name;
    }

    public Command(final String name, final Object... arguments) {
        this();
        this.name = name;
        this.arguments = arguments;
    }
    
    public Command(final String name, final Target target, final Object... arguments) {
        this();
        this.name = name;
        this.arguments = arguments;
        setTarget(target);
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

    public String variableName() {
        return variable;
    }

    public void setVariable(final String variable) {
        if( null != variable && !variable.matches("@@.*@@")) {
            throw new IllegalVariableArgumentException();
        }
        this.variable = variable;
    }

    public void setTarget(final Target target) {
        if (target != null) {
            this.target = target;
        }
    }

    public boolean hasVariable() {
        return variable != null;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + Arrays.hashCode(arguments);
    }

    @Override
    public boolean equals(Object other) {
        if (null == other || !(other instanceof Command)) {
            return false;
        }

        final Command otherCommand = (Command) other;
        return Objects.equal(name, otherCommand.getName()) && Arrays.equals(arguments, otherCommand.getArguments());
    }
}