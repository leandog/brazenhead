package com.leandog.brazenhead;

import android.view.View;
import android.widget.*;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.exceptions.IsNotAListViewItem;

public class ListItemFinder {

    public View findByText(final String itemText) throws Exception {
        waitForText(itemText, solo());

        final TextView theFoundText = solo().getText(itemText);

        View foundListItem = null;
        View theParent = theParentOf(theFoundText);
        
        while( atTheRootViewFor(theParent)) {
            foundListItem = theParent;
            theParent = theParentOf(theParent);
        }

        assertWasAListItem(foundListItem, theParent);
        return foundListItem;
    }

    private Solo solo() {
        return TestRunInformation.getSolo();
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

}
