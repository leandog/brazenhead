package com.leandog.gametel.driver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.leandog.gametel.driver.test.GametelTestRunner;

import android.os.Bundle;

@RunWith(GametelTestRunner.class)
public class GametelInstrumentationTest {
    
    @Test
    public void itInitializesTheTestRunInformation() {
        Bundle arguments = new Bundle();
        arguments.putString("packageName", "thePackage");
        arguments.putString("fullLauncherName", "theActivity");
        new GametelInstrumentation().onCreate(arguments);
        
        assertThat(TestRunInformation.getPackageName(), is("thePackage"));
        assertThat(TestRunInformation.getFullLauncherName(), is("theActivity"));
    }

}
