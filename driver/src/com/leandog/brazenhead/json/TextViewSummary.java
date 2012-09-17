package com.leandog.brazenhead.json;

import android.widget.TextView;

@SuppressWarnings("unused")
public class TextViewSummary extends ViewSummary {

    private CharSequence text;
    private CharSequence hint;
    private CharSequence contentDescription;

    public TextViewSummary(TextView theView) {
        super(theView);
        text = theView.getText();
        hint = theView.getHint();
        contentDescription = theView.getContentDescription();
    }

}