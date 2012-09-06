package com.leandog.brazenhead.commands;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

import com.leandog.brazenhead.TestRunInformation;
import com.leandog.brazenhead.commands.Command.Target;
import com.leandog.brazenhead.exceptions.CommandNotFoundException;

public class CommandRunner {

    private Object theLastResult;
    
    Map<String, Object> variables =  new HashMap<String, Object>();

    public void execute(final Command... commands) throws Exception {
        clearLastRun();

        for (final Command command : commands) {
            theLastResult = findMethod(command).invoke(theTargetFor(command), theArguments(command));
            
            if( command.hasVariable() ) {
                storeVariable(command);
            }
        }
    }

    public Object theLastResult() {
        return theLastResult;
    }

    private Method findMethod(final Command command) throws CommandNotFoundException {
        return new MethodFinder()
            .find(command.getName())
            .on(theTargetFor(command).getClass())
            .with(argumentTypesFor(command));
    }

    private void clearLastRun() {
        theLastResult = null;
        variables.clear();
    }

    private Object theTargetFor(final Command command) {
        if( command.getTarget() == Target.Robotium ) {
            return TestRunInformation.getSolo();
        }
        
        return (theLastResult == null) ? TestRunInformation.getSolo() : theLastResult;
    }

    private static Map<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>();
    static {
        primitiveMap.put(Integer.class, int.class);
        primitiveMap.put(Boolean.class, boolean.class);
        primitiveMap.put(Float.class, float.class);
    }

    private Class<? extends Object> typeFor(final Object argument) {
        Class<?> argumentType = argument.getClass();
        Class<?> primitiveType = primitiveMap.get(argumentType);
        return (primitiveType == null) ? argumentType : primitiveType;
    }

    private Class<?>[] argumentTypesFor(final Command command) {
        Class<?>[] types = new Class<?>[theArguments(command).length];

        int index = 0;
        for (final Object argument : theArguments(command)) {
            types[index++] = typeFor(argument);
        }

        return types;
    }

    private Object[] theArguments(final Command command) {
        List<Object> arguments = Arrays.asList(command.getArguments());
        substituteVariables(arguments);
        return arguments.toArray();
    }

    private void substituteVariables(List<Object> arguments) {
        for(final Entry<String, Object> variable : variables.entrySet()) {
            Collections.replaceAll(arguments, variable.getKey(), variable.getValue());
        }
    }

    private void storeVariable(final Command command) {
        variables.put(command.getVariable(), theLastResult);
    }

}
