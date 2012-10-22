package com.leandog.brazenhead;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class BrazenheadTest {
    
    @Mock Solo solo;
    @Mock Activity activity;
    @Mock Resources resources;
    @Mock SpinnerPresser spinnerPresser;
    @Mock ListItemFinder listItemFinder;
    @Mock ListItemPresser listItemPresser;

    private Brazenhead brazenhead;

    @Before
    public void setUp() throws IOException {
        initMocks();
        brazenhead = new Brazenhead(spinnerPresser, listItemFinder, listItemPresser);
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
    
    @Test
    public void itCanLocateListItemsByIndexInTheDefaultList() {
        brazenhead.listItemByIndex(7);
        verify(listItemFinder).findByIndex(7);
    }
    
    @Test
    public void itCanLocateListItemsByIndex() {
        brazenhead.listItemByIndex(7, 1);
        verify(listItemFinder).findByIndex(7, 1);
    }
    
    @Test
    public void itCanPressListItemsByIndex() throws Exception {
        brazenhead.pressListItemByIndex(7);
        verify(listItemPresser).pressListItem(7);
    }
    
    @Test
    public void itCanPressASpecificListsItems() {
        int whichList = 1;
        brazenhead.pressListItemByIndex(7, whichList);
        verify(listItemPresser).pressListItem(7, whichList);
    }

    private void initMocks() {
        TestRunInformation.setSolo(solo);
        when(solo.getCurrentActivity()).thenReturn(activity);
        when(activity.getResources()).thenReturn(resources);
    }

}
