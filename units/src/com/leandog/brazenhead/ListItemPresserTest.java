package com.leandog.brazenhead;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.view.View;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class ListItemPresserTest {
    
    @Mock Solo solo;
    @Mock ListItemFinder listItemFinder;
    @Mock View theFoundItem;
    
    private ListItemPresser listItemPresser;
    
    @Before
    public void setUp() {
        initMocks();
        listItemPresser = new ListItemPresser(solo, listItemFinder);
    }
    
    @Test
    public void itCanPressUsingTheDefaultList() {
        listItemPresser.pressListItem(7);
        verify(listItemFinder).findByIndex(7, 0);
        verify(solo).clickOnView(theFoundItem);
    }
    
    @Test
    public void itCanPressUsingASpecificList() {
        listItemPresser.pressListItem(7, 2);
        verify(listItemFinder).findByIndex(7, 2);
        verify(solo).clickOnView(theFoundItem);
    }

    private void initMocks() {
        when(listItemFinder.findByIndex(anyInt()))
            .thenReturn(theFoundItem);
        when(listItemFinder.findByIndex(anyInt(), anyInt()))
            .thenReturn(theFoundItem);
    }
}
