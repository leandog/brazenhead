package com.leandog.brazenhead;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.app.*;
import android.content.res.Resources;
import android.widget.*;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class BrazenheadTest {
    
    @Mock Solo solo;
    @Mock Instrumentation instrumentation;
    @Mock Activity activity;
    @Mock Resources resources;
    @Mock SpinnerPresser spinnerPresser;
    @Mock ListItemFinder listItemFinder;

    private Brazenhead brazenhead;

    @Before
    public void setUp() throws IOException {
        initMocks();
        brazenhead = new Brazenhead(instrumentation, spinnerPresser, listItemFinder);
    }

    @Test
    public void itCanFindAnIdFromTheTargetPackageResources() {
        when(activity.getPackageName()).thenReturn("com.some.package");

        brazenhead.idFromName("some_id");

        verify(resources).getIdentifier("some_id", "id", "com.some.package");
    }

    @Test
    public void itCanPressSpinnersById() {
        brazenhead.pressSpinnerItemById(123, 7);
        verify(spinnerPresser).pressSpinnerItemById(123, 7);
    }

    @Test
    public void itCanPressSpinnersByView() {
        final Spinner spinner = mock(Spinner.class);
        brazenhead.pressSpinnerItem(spinner, 7);
        verify(spinnerPresser).pressSpinnerItem(spinner, 7);
    }
    
    @Test
    public void itCanLocateListItemsByText() throws Exception {
        brazenhead.listItemByText("Some text");
        verify(listItemFinder).findByText("Some text");
    }

    private void initMocks() {
        TestRunInformation.setSolo(solo);
        when(solo.getCurrentActivity()).thenReturn(activity);
        when(activity.getResources()).thenReturn(resources);
    }

}
