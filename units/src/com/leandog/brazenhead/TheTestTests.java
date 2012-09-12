package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.app.Activity;
import android.os.Bundle;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class TheTestTests {
    
    @Mock BrazenheadServer brazenheadServer;
    @Mock Solo solo;
    
    TheTest theTest;
    
    class FakeActivity extends Activity {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException {
        makeAndroidHappy();
        theTest = new TheTest();
        theTest.brazenheadServer = brazenheadServer;
    }
    
    @Test
    public void itStartsTheServer() throws Exception {
        theTest.testAllTheThings();
        verify(brazenheadServer).start();
    }
    
    @Test
    public void itWaitsUntilTheServerFinishes() throws Exception {
        theTest.testAllTheThings();
        verify(brazenheadServer).waitUntilFinished();
    }
    
    @Test
    public void itInitializesRobotiumOnSetUp() throws Exception {
        theTest.setUp();
        assertThat(TestRunInformation.getSolo(), is(notNullValue()));
    }
    
    @Test
    public void itInitializesBrazenheadOnSetUp() throws Exception {
        theTest.setUp();
        assertThat(TestRunInformation.getBrazenhead(), instanceOf(Brazenhead.class));
    }
    
    @Test
    public void brazenheadHasTheInstrumentationContext() throws Exception { 
        theTest.setUp();
        assertThat(TestRunInformation.getBrazenhead().getInstrumentation(), is(notNullValue()));
    }
    
    @Test
    public void itInitializesTheBrazenheadServerOnSetUp() throws Exception {
        theTest.setUp();
        assertThat(theTest.brazenheadServer, is(notNullValue()));
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
