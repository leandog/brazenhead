package com.leandog.brazenhead.exceptions;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import org.junit.Test;

import com.leandog.brazenhead.commands.Command;
import com.leandog.brazenhead.exceptions.CommandNotFoundException;

public class CommandNotFoundExceptionTest {

    Command theCommand = new Command("theCommandMethod");

    @Test
    public void itIndicatedWhichMethodWasNotFound() {
        final CommandNotFoundException exception = new CommandNotFoundException(theCommand, "");
        assertThat(exception.getMessage(), containsString("The theCommandMethod() method was not found"));
    }

    @Test
    public void itIndicatesTheClassItTriedToFindItIn() {
        final CommandNotFoundException exception = new CommandNotFoundException(theCommand, new Integer(0));
        assertThat(exception.getMessage(), containsString("was not found on java.lang.Integer"));
    }
    
    @Test
    public void itIndicatesIfThereWereNoArguments() {
        final CommandNotFoundException exception = new CommandNotFoundException(theCommand, new Integer(0));
        assertThat(exception.getMessage(), containsString("The theCommandMethod()"));
    }
    
    @Test
    public void itIndicatesIfThereWereArgumentTypes() {
        final CommandNotFoundException exception = new CommandNotFoundException(theCommand, "", Integer.class, String.class);
        assertThat(exception.getMessage(), containsString("theCommandMethod(java.lang.Integer, java.lang.String)"));
    }

}
