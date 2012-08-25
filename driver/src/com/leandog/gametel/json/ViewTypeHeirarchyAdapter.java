package com.leandog.gametel.json;

import java.lang.reflect.Type;

import android.view.View;

import com.google.gson.*;

public class ViewTypeHeirarchyAdapter implements JsonSerializer<View> {

    @SuppressWarnings("unused")
    public class JsonView {
        private final int id;
        private final String classType;
        
        private final int width;
        private final int height;
        
        private final int[] screenLocation = new int[2];
        private final int[] windowLocation = new int[2];
        
        private final int left;
        private final int top;
        private final int right;
        private final int bottom;

        public JsonView(final View theView) {
            id = theView.getId();
            classType = theView.getClass().getName();
            
            width = theView.getWidth();
            height = theView.getHeight();
            
            theView.getLocationOnScreen(screenLocation);
            theView.getLocationInWindow(windowLocation);
            
            left = theView.getLeft();
            top = theView.getTop();
            right = theView.getRight();
            bottom = theView.getBottom();
        }
    }

    @Override
    public JsonElement serialize(View theView, Type type, JsonSerializationContext context) {
        return new Gson().toJsonTree(new JsonView(theView));
    }

}
