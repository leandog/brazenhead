package com.leandog.brazenhead;

import android.os.Bundle;
import android.test.InstrumentationTestRunner;

public class BrazenheadInstrumentation extends InstrumentationTestRunner {

    @Override
    public void onCreate(Bundle arguments) {
        TestRunInformation.initialize(arguments);
        super.onCreate(arguments);
    }

}
