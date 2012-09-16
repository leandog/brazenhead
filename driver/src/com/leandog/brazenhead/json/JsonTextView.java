package com.leandog.brazenhead.json;

import android.widget.TextView;

@SuppressWarnings("unused")
public class JsonTextView extends JsonView {

    private CharSequence text;
    private CharSequence hint;
    private CharSequence contentDescription;

    public JsonTextView(TextView theView) {
        super(theView);
        text = theView.getText();
        hint = theView.getHint();
        contentDescription = theView.getContentDescription();
    }

}