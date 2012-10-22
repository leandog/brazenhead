package com.leandog.brazenhead.exceptions;

import android.view.View;

@SuppressWarnings("unused")
public class IsNotAListViewItem extends Exception {
    private static final long serialVersionUID = 1L;
    private final View theView;

    public IsNotAListViewItem(final View theView) {
        this.theView = theView;
    }

}
