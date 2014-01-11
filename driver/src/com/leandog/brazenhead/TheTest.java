package com.leandog.brazenhead;

import android.test.ActivityInstrumentationTestCase2;

import com.leandog.brazenhead.server.JettyServer;
import com.robotium.solo.Solo;

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
        TestRunInformation.setBrazenhead(new Brazenhead(getInstrumentation(), TestRunInformation.getSolo()));
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