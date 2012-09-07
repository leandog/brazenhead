package com.leandog.brazenhead.json;

import java.lang.reflect.Type;

import com.google.brazenhead.gson.*;
import com.leandog.brazenhead.commands.*;
import com.leandog.brazenhead.commands.Command.Target;

public class CommandDeserializer implements JsonDeserializer<Command> {

    @Override
    public Command deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;
        Command command = new Command(getName(jsonObject), getArguments(jsonObject, context));
        command.setVariable(getVariable(jsonObject));
        command.setTarget(getTarget(jsonObject, context));
        return command;
    }

    private String getVariable(JsonObject jsonObject) {
        JsonElement variable = jsonObject.get("variable");
        return (variable != null) ? variable.getAsString() : null;
    }

    private Target getTarget(JsonObject jsonObject, JsonDeserializationContext context) {
        JsonElement targetElement = jsonObject.get("target");
        return (targetElement != null) ? (Target)context.deserialize(targetElement, Target.class) : null;
    }

    private String getName(JsonObject jsonObject) {
        JsonElement nameElement = jsonObject.get("name");
        return (nameElement != null) ? nameElement.getAsString() : null;
    }

    private Object[] getArguments(JsonObject jsonObject, JsonDeserializationContext context) {
        final JsonElement argumentElement = jsonObject.get("arguments");
        if (isNotAnArray(argumentElement)) {
            return new Object[0];
        }

        final JsonArray jsonArray = argumentElement.getAsJsonArray();

        Object[] arguments = new Object[jsonArray.size()];
        for (int index = 0; index < jsonArray.size(); index++) {
            final JsonElement element = jsonArray.get(index);
            arguments[index] = asPrimitive(element);
        }

        return arguments;
    }

    private boolean isNotAnArray(final JsonElement argumentElement) {
        return null == argumentElement || argumentElement.isJsonNull() || !argumentElement.isJsonArray();
    }

    private Object asPrimitive(final JsonElement element) {
        if( !element.isJsonPrimitive() ) return null;
        
        JsonPrimitive primitive = (JsonPrimitive) element;

        if (primitive.isBoolean())
            return primitive.getAsBoolean();
        if (primitive.isString())
            return primitive.getAsString();
        if (primitive.isNumber())
            return asNumber(primitive);

        throw new IllegalArgumentException();
    }

    private Object asNumber(JsonPrimitive primitive) {
        Object argument;
        try {
            argument = primitive.getAsInt();
        } catch (Exception e) {
            argument = primitive.getAsFloat();
            if (isProbablyADouble(argument)) {
                argument = primitive.getAsDouble();
            }
        }
        return argument;
    }

    private boolean isProbablyADouble(Object argument) {
        return (Float) argument == Float.POSITIVE_INFINITY;
    }

}
