package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.Bundle;

import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class BrazenheadInstrumentationTest {
    
    @Test
    public void itInitializesTheTestRunInformation() {
        Bundle arguments = new Bundle();
        arguments.putString("packageName", "thePackage");
        arguments.putString("fullLauncherName", "theActivity");
        new BrazenheadInstrumentation().onCreate(arguments);
        
        assertThat(TestRunInformation.getPackageName(), is("thePackage"));
        assertThat(TestRunInformation.getFullLauncherName(), is("theActivity"));
    }

}
