package com.leandog.gametel.driver.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.TestRunInformation;

public class CommandRunner {

    public void execute(final Command command) {
        try {
            final Method method = Solo.class.getMethod(command.getName());
            method.invoke(TestRunInformation.getSolo());
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
