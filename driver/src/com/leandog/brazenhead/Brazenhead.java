package com.leandog.brazenhead;

import android.app.Instrumentation;

public class Brazenhead {

    private final Instrumentation instrumentation;

    public Brazenhead(final Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }
    
    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

}
