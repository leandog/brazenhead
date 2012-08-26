package com.leandog.gametel.driver.exceptions;

import com.leandog.gametel.driver.commands.Command;

public class CommandNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CommandNotFoundException(final Command command, final Object theTarget, final Class<?>... arguments) {
        super(missingCommandMessage(command, theTarget, arguments));
    }

    private static String missingCommandMessage(final Command command, final Object theTarget, final Class<?>... arguments) {
        return String.format("The %s%s method was not found on %s.",
                command.getName(),
                stringFor(arguments),
                theTarget.getClass().getName());
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
