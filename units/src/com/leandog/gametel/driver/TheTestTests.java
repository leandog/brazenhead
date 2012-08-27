package com.leandog.gametel.driver;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.app.Activity;
import android.os.Bundle;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.test.GametelTestRunner;

@RunWith(GametelTestRunner.class)
public class TheTestTests {
    
    @Mock GametelServer gametelServer;
    @Mock Solo solo;
    
    TheTest theTest;
    
    class FakeActivity extends Activity {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException {
        makeAndroidHappy();
        theTest = new TheTest();
        theTest.gametelServer = gametelServer;
    }
    
    @Test
    public void itStartsTheServer() throws Exception {
        theTest.testAllTheThings();
        verify(gametelServer).start();
    }
    
    @Test
    public void itWaitsUntilTheServerFinishes() throws Exception {
        theTest.testAllTheThings();
        verify(gametelServer).waitUntilFinished();
    }
    
    @Test
    public void itInitializesRobotiumOnSetUp() throws Exception {
        theTest.setUp();
        assertThat(TestRunInformation.getSolo(), is(notNullValue()));
    }
    
    @Test
    public void itInitializesTheGametelServerOnSetUp() throws Exception {
        theTest.setUp();
        assertThat(theTest.gametelServer, is(notNullValue()));
    }
    
    @Test
    public void itFinishesOpenActivitiesOnTearDown() throws Exception {
        TestRunInformation.setSolo(solo);
        theTest.tearDown();
        verify(solo).finishOpenedActivities();
    }

    private void makeAndroidHappy() {
        Bundle arguments = new Bundle();
        arguments.putString("packageName", FakeActivity.class.getPackage().getName());
        arguments.putString("fullLauncherName", FakeActivity.class.getName());
        TestRunInformation.initialize(arguments);
    }
}
