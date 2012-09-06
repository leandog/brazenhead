package com.leandog.brazenhead.exceptions;

import com.leandog.brazenhead.commands.Command;

public class CommandNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CommandNotFoundException(final String methodName, final Class<?> targetClass, final Class<?>... arguments) {
        super(missingCommandMessage(methodName, targetClass, arguments));
    }

    public CommandNotFoundException(final Command command, final Object theTarget, final Class<?>... arguments) {
        super(missingCommandMessage(command.getName(), theTarget.getClass(), arguments));
    }

    private static String missingCommandMessage(final String methodName, final Class<?> targetClass, final Class<?>... arguments) {
        return String.format("The %s%s method was not found on %s.",
                methodName,
                stringFor(arguments),
                targetClass.getName());
    }

    private static String stringFor(Class<?>[] arguments) {
        if (arguments.length == 0) {
            return "()";
        }

        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("(");
        for (final Class<?> argumentType : arguments) {
            messageBuilder.append(argumentType.getName() + ", ");
        }

        messageBuilder.replace(last(messageBuilder, 2), last(messageBuilder, 0), ")");
        return messageBuilder.toString();
    }

    private static int last(final StringBuilder messageBuilder, int count) {
        return messageBuilder.length() - count;
    }
}
