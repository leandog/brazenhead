package com.leandog.gametel.driver.test;

import java.io.File;

import org.junit.runners.model.InitializationError;

import com.xtremelabs.robolectric.RobolectricTestRunner;

public class GametelTestRunner extends RobolectricTestRunner {

    public GametelTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass, new File("../driver"));
    }

}
