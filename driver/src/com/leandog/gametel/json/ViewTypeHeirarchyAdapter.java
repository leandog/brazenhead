package com.leandog.gametel.json;

import java.lang.reflect.Type;

import android.view.View;

import com.google.gson.*;

public class ViewTypeHeirarchyAdapter implements JsonSerializer<View> {
    
    public class JsonView {

        public JsonView(final View theView) {
        }
    }

    @Override
    public JsonElement serialize(View theView, Type type, JsonSerializationContext context) {
        return new Gson().toJsonTree(new JsonView(theView));
    }

}
