package com.leandog.brazenhead.json;

import java.lang.reflect.Type;

import android.view.View;

import com.google.brazenhead.gson.*;

public class ViewJsonSerializer implements JsonSerializer<View> {

    @Override
    public JsonElement serialize(View theView, Type type, JsonSerializationContext context) {
        return new Gson().toJsonTree(new JsonView(theView));
    }

}
