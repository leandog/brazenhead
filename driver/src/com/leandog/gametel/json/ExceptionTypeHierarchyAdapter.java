package com.leandog.gametel.json;

import java.lang.reflect.Type;

import com.google.gson.*;

public class ExceptionTypeHierarchyAdapter implements JsonSerializer<Exception> {

    public class ExceptionSummary {

        public final String exception;
        public final String errorMessage;

        public final ExceptionSummary theCause;

        public ExceptionSummary(final Throwable exception) {
            this.exception = exception.getClass().getName();
            this.errorMessage = exception.getMessage();
            this.theCause = theCause(exception);
        }

        private ExceptionSummary theCause(final Throwable exception) {
            Throwable cause = exception.getCause();
            if (null == cause) {
                return null;
            }

            return new ExceptionSummary(exception.getCause());
        }

    }

    @Override
    public JsonElement serialize(Exception theException, Type type, JsonSerializationContext context) {
        return new Gson().toJsonTree(new ExceptionSummary(theException));
    }

}
