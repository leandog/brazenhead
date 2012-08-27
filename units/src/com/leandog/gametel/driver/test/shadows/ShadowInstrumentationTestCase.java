package com.leandog.gametel.driver.test.shadows;

import android.app.Instrumentation;
import android.test.*;

import com.xtremelabs.robolectric.internal.*;

@Implements(InstrumentationTestCase.class)
public class ShadowInstrumentationTestCase {
    
    @Implementation
    public Instrumentation getInstrumentation() {
        return new Instrumentation();
    }
    
}