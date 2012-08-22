package com.leandog.gametel.driver.commands;

import java.lang.reflect.Method;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.TestRunInformation;

public class CommandRunner {

    private Object theLastResult;

    public void execute(final Command command) {
        try {
            final Method method = Solo.class.getMethod(command.getName());
            theLastResult = method.invoke(TestRunInformation.getSolo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object theLastResult() {
        return theLastResult;
    }

}
