package com.leandog.brazenhead;

import android.app.*;
import android.content.res.Resources;
import android.view.View;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.*;

public class Brazenhead {

    private final SpinnerPresser spinnerPresser;
    private final ListItemFinder listItemFinder;
    private ListItemPresser listItemPresser;

    public Brazenhead(final Instrumentation instrumentation, final Solo solo) {
        this.spinnerPresser = new SpinnerPresser(instrumentation);
        this.listItemFinder = new ListItemFinder(instrumentation, solo, new BrazenheadSleeper());
        this.listItemPresser = new ListItemPresser(solo, listItemFinder);
    }

    public Brazenhead(final SpinnerPresser spinnerPresser, final ListItemFinder listItemFinder, final ListItemPresser listItemPresser) {
        this.spinnerPresser = spinnerPresser;
        this.listItemFinder = listItemFinder;
        this.listItemPresser = listItemPresser;
    }

    public int idFromName(final String namedId) {
        return getResources().getIdentifier(namedId, "id", targetPackage());
    }

    public void pressSpinnerItem(final Spinner spinner, int itemIndex) {
        spinnerPresser.pressSpinnerItem(spinner, itemIndex);
    }

    public void pressSpinnerItemById(int spinnerId, int itemIndex) {
        spinnerPresser.pressSpinnerItemById(spinnerId, itemIndex);
    }

    public View listItemByText(final String itemText) throws Exception {
        return listItemFinder.findByText(itemText);
    }
    
    public View listItemByIndex(final int itemIndex) {
        return listItemFinder.findByIndex(itemIndex);
    }
    
    public View listItemByIndex(final int itemIndex, final int whichList) {
        return listItemFinder.findByIndex(itemIndex, whichList);
    }

    public void pressListItemByIndex(final int itemIndex) {
        listItemPresser.pressListItem(itemIndex);
    }

    public void pressListItemByIndex(final int itemIndex, final int whichList) {
        listItemPresser.pressListItem(itemIndex, whichList);
    }

    private Activity theCurrentActivity() {
        return TestRunInformation.getSolo().getCurrentActivity();
    }

    private Resources getResources() {
        return theCurrentActivity().getResources();
    }

    private String targetPackage() {
        return theCurrentActivity().getPackageName();
    }

}
