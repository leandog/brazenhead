package com.leandog.brazenhead;

import android.app.*;
import android.content.res.Resources;
import android.widget.Spinner;

public class Brazenhead {

    private final SpinnerPresser spinnerPresser;

    public Brazenhead(final Instrumentation instrumentation) {
        spinnerPresser = new SpinnerPresser(instrumentation);
    }

    public Brazenhead(final Instrumentation instrumentation, final SpinnerPresser spinnerPresser) {
        this.spinnerPresser = spinnerPresser;
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
