package com.leandog.gametel.driver.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.TestRunInformation;

public class CommandRunner {

    private Object theLastResult;

    public void execute(final Command command) {
        try {
            final Method method = finaMethodFor(command);
            theLastResult = method.invoke(TestRunInformation.getSolo(), command.getArguments());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method finaMethodFor(final Command command) throws NoSuchMethodException {
        List<Class<?>> types = new ArrayList<Class<?>>();
        for(final Object argument : command.getArguments()) {
            Class<? extends Object> argumentType = argument.getClass();
            if( argumentType.equals(Integer.class) ) {
                argumentType = int.class;
            }
            types.add(argumentType);
        }
        
        Class<?>[] typesArray = new Class<?>[types.size()];
        types.toArray(typesArray);
        return Solo.class.getMethod(command.getName(), typesArray);
    }

    public Object theLastResult() {
        return theLastResult;
    }

}
