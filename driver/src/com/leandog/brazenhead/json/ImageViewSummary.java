package com.leandog.brazenhead.json;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

@SuppressWarnings("unused")
public class ImageViewSummary extends ViewSummary {

    boolean hasDrawable;
    Rect drawableRect;

    public ImageViewSummary(ImageView theView) {
        super(theView);
        
        hasDrawable = theView.getDrawable() != null;
        
        if( hasDrawable ) {
            drawableRect = theView.getDrawable().getBounds();
        }
    }

}
