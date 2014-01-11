package com.leandog.brazenhead;

import com.robotium.solo.Solo;

public class ListItemPresser {

    private final Solo solo;
    private final ListItemFinder listItemFinder;

    public ListItemPresser(final Solo solo, final ListItemFinder listItemFinder) {
        this.solo = solo;
        this.listItemFinder = listItemFinder;
    }

    public void pressListItem(final int itemIndex) {
        pressListItem(itemIndex, 0);
    }

    public void pressListItem(int itemIndex, final int listIndex) {
        solo.clickOnView(listItemFinder.findByIndex(itemIndex, listIndex));
    }


}
