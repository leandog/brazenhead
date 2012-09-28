package com.leandog.brazenhead;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.app.Activity;
import android.content.res.Resources;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class BrazenheadTest {
    
    @Mock Solo solo;
    @Mock Activity activity;
    @Mock Resources resources;
    
    private Brazenhead brazenhead;
    
    @Before
    public void setUp() throws IOException {
        initMocks();
        brazenhead = new Brazenhead();
    }
    
    @Test
    public void itCanFindAnIdFromTheTargetPackageResources() {
        when(activity.getPackageName())
            .thenReturn("com.some.package");
        
        brazenhead.idFromName("some_id");
        
        verify(resources).getIdentifier("some_id", "id", "com.some.package");
    }

    private void initMocks() {
        TestRunInformation.setSolo(solo);
        when(solo.getCurrentActivity()).thenReturn(activity);
        when(activity.getResources()).thenReturn(resources);
    }

}
