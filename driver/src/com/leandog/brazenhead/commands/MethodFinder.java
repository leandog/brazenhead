package com.leandog.brazenhead.commands;

import java.lang.reflect.Method;
import java.util.*;

import com.leandog.brazenhead.exceptions.CommandNotFoundException;

public class MethodFinder {

    private String methodName;
    private Class<?> targetClass;

    public MethodFinder find(final String methodName) {
        this.methodName = methodName;
        return this;
    }

    public MethodFinder on(final Class<?> targetClazz) {
        this.targetClass = targetClazz;
        return this;
    }

    public Method with(final Class<?>[] argumentTypes) throws CommandNotFoundException {
        for(final Method candidate : candidatesFor(targetClass) ) {
            if(parametersMatch(candidate, argumentTypes)) {
                return candidate;
            }
        }
        
        throw new CommandNotFoundException(methodName, targetClass, argumentTypes);
    }

    private List<Method> candidatesFor(Class<?> clazz) {
        List<Method> methods = new ArrayList<Method>();
        
        for(final Method candidate : clazz.getMethods()) {
            if( candidate.getName().equals(methodName) ) {
                methods.add(candidate);
            }
        }
        
        return methods;
    }

    private boolean parametersMatch(final Method candidate, final Class<?>[] expectedTypes) {
        final Class<?>[] actualTypes = candidate.getParameterTypes();
        if (argumentCountDiffers(expectedTypes, actualTypes)) {
            return false;
        }

        for (int index = 0; index < actualTypes.length; index++) {
            if (!actualTypes[index].isAssignableFrom(expectedTypes[index])) {
                return false;
            }
        }

        return true;
    }

    private boolean argumentCountDiffers(Class<?>[] expectedTypes, final Class<?>[] actualTypes) {
        return actualTypes.length != expectedTypes.length;
    }
}
