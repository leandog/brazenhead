package com.leandog.brazenhead;

import android.view.View;
import android.widget.*;

import com.jayway.android.robotium.solo.Solo;

public class ListItemFinder {

    public View findByText(final String itemText) {
        final Solo solo = TestRunInformation.getSolo();

        if( !solo.waitForText(itemText) ) {
            return null;
        }
        
        final TextView theFoundText = solo.getText(itemText);

        View foundListItem = null;

        for (View theParent = (View) theFoundText.getParent(); theParent != null && !(theParent instanceof AbsListView);) {
            foundListItem = theParent;
            theParent = (View) theParent.getParent();
        }

        return foundListItem;
    }

}
