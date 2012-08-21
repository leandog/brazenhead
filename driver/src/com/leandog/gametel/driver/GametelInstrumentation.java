package com.leandog.gametel.driver;

import android.os.Bundle;
import android.test.InstrumentationTestRunner;

public class GametelInstrumentation extends InstrumentationTestRunner {

    @Override
    public void onCreate(Bundle arguments) {
        TestRunInformation.initialize(arguments);
        super.onCreate(arguments);
    }

    @Override
    public void onStart() {
       super.onStart();
    }

}
