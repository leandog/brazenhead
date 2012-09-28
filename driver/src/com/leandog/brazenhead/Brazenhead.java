package com.leandog.brazenhead;

import android.app.Activity;
import android.content.res.Resources;

public class Brazenhead {

    public int idFromName(final String namedId) {
        return getResources().getIdentifier(namedId, "id", targetPackage());
    }

    private Activity theCurrentActivity() {
        return TestRunInformation.getSolo().getCurrentActivity();
    }
    
    private Resources getResources() {
        final Resources resources = theCurrentActivity().getResources();
        return resources;
    }

    private String targetPackage() {
        return theCurrentActivity().getPackageName();
    }

}
