package com.leandog.gametel.driver.commands;

import java.lang.reflect.Method;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.TestRunInformation;

public class CommandRunner {

    private Object theLastResult;

    public void execute(final Command command) {
        try {
            final Method method = findMethodFor(command);
            theLastResult = method.invoke(TestRunInformation.getSolo(), command.getArguments());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object theLastResult() {
        return theLastResult;
    }

    private Method findMethodFor(final Command command) throws NoSuchMethodException {
        
        return Solo.class.getMethod(command.getName(), argumentTypesFor(command));
    }

    private Class<? extends Object> typeFor(final Object argument) {
        Class<? extends Object> argumentType = argument.getClass();
        if( argumentType.equals(Integer.class) ) {
            argumentType = int.class;
        }
        return argumentType;
    }
    
    private Class<?>[] argumentTypesFor(final Command command) {
        Class<?>[] types = new Class<?>[command.getArguments().length];
        
        int index = 0;
        for(final Object argument : command.getArguments()) {
            types[index++] = typeFor(argument);
        }
        
        return types;
    }

}
