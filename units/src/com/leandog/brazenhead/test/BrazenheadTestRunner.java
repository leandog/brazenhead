package com.leandog.brazenhead.test;

import static com.xtremelabs.robolectric.Robolectric.bindShadowClass;


import java.io.File;
import java.lang.reflect.Method;

import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;

import com.leandog.brazenhead.test.shadows.*;
import com.xtremelabs.robolectric.RobolectricTestRunner;

public class BrazenheadTestRunner extends RobolectricTestRunner {

    public BrazenheadTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass, new File("../driver"));
    }
    
    @Override
    public Object createTest() throws Exception {
        Object theTest = super.createTest();
        MockitoAnnotations.initMocks(theTest);
        return theTest;
    }
    
    @Override
    public void beforeTest(Method method) {
        bindShadowClass(ShadowInstrumentationTestCase.class);
    }

}
