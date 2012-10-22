package com.leandog.brazenhead;

import android.app.Instrumentation;
import android.view.KeyEvent;
import android.widget.ListView;

import com.jayway.android.robotium.solo.Solo;

public class ListItemPresser {

    private final Instrumentation instrumentation;
    private final Solo solo;

    public ListItemPresser(final Instrumentation instrumentation, final Solo solo) {
        this.instrumentation = instrumentation;
        this.solo = solo;
    }

    public void pressListItem(final int itemIndex) {
        pressListItem(itemIndex, 0);
    }

    public void pressListItem(int itemIndex, final int listIndex) {
        itemIndex = normalize(itemIndex);
        setTheFocus(listIndex);
        setTheSelection(itemIndex, listIndex);
        clickOnItem(listIndex);
    }

    private void setTheSelection(final int itemIndex, final int listIndex) {
        instrumentation.runOnMainSync(new Runnable() {

            @Override
            public void run() {
                theListAt(listIndex).setSelection(itemIndex);
            }
        });
    }

    private ListView theListAt(final int listIndex) {
        solo.waitForView(ListView.class);
        return solo.getCurrentListViews().get(listIndex);
    }

    private void setTheFocus(final int listIndex) {
        theListAt(listIndex).requestFocus();
        sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
    }

    private void clickOnItem(final int listIndex) {
        solo.clickOnView(theListAt(listIndex).getSelectedView());
    }

    private int normalize(int itemIndex) {
        --itemIndex;
        if (itemIndex < 0) {
            itemIndex = 0;
        }

        return itemIndex;
    }

    private void sendKey(int keycode) {
        try {
            instrumentation.sendKeyDownUpSync(keycode);
        } catch (SecurityException ignored) {
        }
    }

}
