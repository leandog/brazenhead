package com.leandog.gametel.driver;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.server.JettyServer;

@SuppressWarnings("rawtypes")
public class TheTest extends ActivityInstrumentationTestCase2 {

    private GametelServer gametelServer;

    @SuppressWarnings("unchecked")
    public TheTest() throws ClassNotFoundException {
        super(TestRunInformation.getPackageName(), Class.forName(TestRunInformation.getFullLauncherName()));
    }

    @Override
    protected void setUp() throws Exception {
        TestRunInformation.setSolo(new Solo(getInstrumentation(), getActivity()));
        gametelServer = new GametelServer(new JettyServer());
    }

    public void testAllTheThings() throws Exception {
        gametelServer.start();
        gametelServer.waitUntilFinished();
    }

    @Override
    public void tearDown() throws Exception {
        TestRunInformation.getSolo().finishOpenedActivities();
    }

}