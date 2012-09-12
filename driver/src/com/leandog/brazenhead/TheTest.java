package com.leandog.brazenhead;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.server.JettyServer;

@SuppressWarnings("rawtypes")
public class TheTest extends ActivityInstrumentationTestCase2 {

    BrazenheadServer brazenheadServer;

    @SuppressWarnings("unchecked")
    public TheTest() throws ClassNotFoundException {
        super(TestRunInformation.getPackageName(), Class.forName(TestRunInformation.getFullLauncherName()));
    }

    @Override
    protected void setUp() throws Exception {
        TestRunInformation.setSolo(new Solo(getInstrumentation(), getActivity()));
        TestRunInformation.setBrazenhead(new Brazenhead(getInstrumentation()));
        brazenheadServer = new BrazenheadServer(new JettyServer());
    }

    public void testAllTheThings() throws Exception {
        brazenheadServer.start();
        brazenheadServer.waitUntilFinished();
    }

    @Override
    public void tearDown() throws Exception {
        TestRunInformation.getSolo().finishOpenedActivities();
    }

}