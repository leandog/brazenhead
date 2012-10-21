package com.leandog.brazenhead.json;

import java.lang.reflect.Type;

import android.widget.ImageView;

import com.google.brazenhead.gson.*;

public class ImageViewSerializer implements JsonSerializer<ImageView> {

    @Override
    public JsonElement serialize(ImageView imageView, Type type, JsonSerializationContext context) {
        return new Gson().toJsonTree(new ImageViewSummary(imageView));
    }

}
