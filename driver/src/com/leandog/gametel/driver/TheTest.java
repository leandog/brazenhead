package com.leandog.gametel.driver;

import org.mortbay.jetty.Server;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public class TheTest extends ActivityInstrumentationTestCase2 {

    private GametelServer gametelServer;
    private Solo solo;

    @SuppressWarnings("unchecked")
    public TheTest() throws ClassNotFoundException {
        super(TestRunInformation.getPackageName(), Class.forName(TestRunInformation.getFullLauncherName()));
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        gametelServer = new GametelServer(solo, new Server());
    }

    public void testAllTheThings() throws Exception {
        gametelServer.start();
        gametelServer.waitUntilFinished();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}