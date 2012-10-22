package com.leandog.brazenhead;

import android.app.Instrumentation;
import android.view.*;
import android.widget.*;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.exceptions.IsNotAListViewItem;

public class ListItemFinder {
    
    private final Solo solo;
    private final Instrumentation instrumentation;

    public ListItemFinder(final Instrumentation instrumentation, final Solo solo) {
        this.instrumentation = instrumentation;
        this.solo = solo;
    }
    
    public View findByIndex(int itemIndex) {
       return findByIndex(itemIndex, 0); 
    }
    
    public View findByIndex(int itemIndex, final int listIndex) {
        itemIndex = normalize(itemIndex);
        setTheFocus(listIndex);
        setTheSelection(itemIndex, listIndex);
        return theListAt(listIndex).getSelectedView();
    }

    public View findByText(final String itemText) throws Exception {
        waitForText(itemText, solo);

        final TextView theFoundText = solo.getText(itemText);

        View foundListItem = theFoundText;
        View theParent = theParentOf(theFoundText);
        
        while( atTheRootViewFor(theParent)) {
            foundListItem = theParent;
            theParent = theParentOf(theParent);
        }
        
        assertWasAListItem(foundListItem, theParent);
        return foundListItem;
    }
    
    private View theParentOf(final View view) {
        return (View) view.getParent();
    }

    private void waitForText(final String itemText, final Solo solo) throws Exception {
        if (!solo.waitForText(itemText)) {
            throw new Exception(String.format("A View with text \"%s\" was not found", itemText));
        }
    }

    private boolean atTheRootViewFor(View theParent) {
        return theParent != null && !(theParent instanceof AbsListView);
    }

    private void assertWasAListItem(View foundListItem, View theParent) throws IsNotAListViewItem {
        if (theParent == null) {
            throw new IsNotAListViewItem(foundListItem);
        }
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
