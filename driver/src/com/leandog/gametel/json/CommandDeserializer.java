package com.leandog.gametel.json;

import java.lang.reflect.Type;

import com.google.gson.*;
import com.leandog.gametel.driver.commands.Command;

public class CommandDeserializer implements JsonDeserializer<Command> {

    @Override
    public Command deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;
        return new Command(getName(jsonObject), getArguments(jsonObject, context));
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

            Object argument = null;
            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = (JsonPrimitive) element;
                if (primitive.isBoolean())
                    argument = primitive.getAsBoolean();
                if (primitive.isString())
                    argument = primitive.getAsString();
                if (primitive.isNumber()) {
                    argument = getNumberValue(primitive);
                }
            } else {
                argument = context.deserialize(argumentElement, Object.class);
            }

            arguments[index] = argument;
        }

        return arguments;
    }

    private boolean isNotAnArray(final JsonElement argumentElement) {
        return null == argumentElement || argumentElement.isJsonNull() || !argumentElement.isJsonArray();
    }

    private Object getNumberValue(JsonPrimitive primitive) {
        Object argument;
        try {
            argument = primitive.getAsInt();
        } catch (Exception e) {
            argument = primitive.getAsFloat();
            if ((Float) argument == Float.POSITIVE_INFINITY) {
                argument = primitive.getAsDouble();
            }
        }
        return argument;
    }

}
