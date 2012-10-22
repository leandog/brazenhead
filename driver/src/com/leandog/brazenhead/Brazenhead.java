package com.leandog.brazenhead;

import android.app.*;
import android.content.res.Resources;
import android.view.View;
import android.widget.Spinner;

public class Brazenhead {

    private final SpinnerPresser spinnerPresser;
    private final ListItemFinder listItemFinder;

    public Brazenhead(final Instrumentation instrumentation) {
        spinnerPresser = new SpinnerPresser(instrumentation);
        listItemFinder = new ListItemFinder();
    }

    public Brazenhead(final Instrumentation instrumentation, final SpinnerPresser spinnerPresser, final ListItemFinder listItemFinder) {
        this.spinnerPresser = spinnerPresser;
        this.listItemFinder = listItemFinder;
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
