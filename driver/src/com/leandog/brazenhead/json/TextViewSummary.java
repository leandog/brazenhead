package com.leandog.brazenhead.json;

import android.widget.TextView;

@SuppressWarnings("unused")
public class TextViewSummary extends ViewSummary {

    private String text;
    private String hint;
    private String contentDescription;

    public TextViewSummary(TextView theView) {
        super(theView);
        text = string(theView.getText());
        hint = string(theView.getHint());
        contentDescription = string(theView.getContentDescription());
    }

    private String string(final CharSequence charSequence) {
        if( charSequence != null) {
            return charSequence.toString();
        }
        
        return null;
    }

}