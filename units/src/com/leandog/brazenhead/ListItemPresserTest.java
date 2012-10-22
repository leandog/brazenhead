package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;

import android.app.Instrumentation;
import android.view.KeyEvent;
import android.widget.ListView;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class ListItemPresserTest {
    
    @Spy InstrumentationStub instrumentation = new InstrumentationStub();
    @Mock ArrayList<ListView> theLists;
    @Mock ListView theFirstList;
    @Mock ListView theSecondList;
    @Mock Solo solo;
    
    private ListItemPresser listItemPresser;
    
    @Before
    public void setUp() {
        initMocks();
        listItemPresser = new ListItemPresser(instrumentation, solo);
    }

    @Test
    public void itBringsTheListViewIntoFocusInitially() {
        listItemPresser.pressListItem(0);
        verify(instrumentation).sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
    }

    @Test
    public void itUsesTheFirstListByDefault() {
        listItemPresser.pressListItem(7);
        verify(solo).getCurrentListViews();
        verify(theLists).get(0);
    }
    
    @Test
    public void itCanUseAnAlternateList() {
        listItemPresser.pressListItem(7, 1);
        verify(solo).getCurrentListViews();
        verify(theLists).get(1);
    }
    
    @Test
    public void selectingListItemsAreOneBaseIndexed() {
        listItemPresser.pressListItem(7);
        verify(theFirstList).setSelection(6);
    }
    
    @Test
    public void listSelectionsAreDoneOnTheUiThread() {
        listItemPresser.pressListItem(7);
        verify(instrumentation).runOnMainSync(any(Runnable.class));
    }
    
    @Test
    public void theFirstItemIsBothIndexZeroAndOne() {
        listItemPresser.pressListItem(0);
        verify(theFirstList).setSelection(0);
    }
    
    @Test
    public void itSelectsTheItem() {
        listItemPresser.pressListItem(7);
        verify(instrumentation).sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
    }
    
    @Test
    public void itCanHandleInjectKeyEventExceptions() {
        instrumentation.setToThrowOnKeyEvents();
        listItemPresser.pressListItem(7);
        assertThat("We should have gotten here", is(notNullValue()));
    }

    private void initMocks() {
        when(solo.getCurrentListViews()).thenReturn(theLists);
        when(theLists.get(0)).thenReturn(theFirstList);
        when(theLists.get(1)).thenReturn(theSecondList);
    }
    
    private class InstrumentationStub extends Instrumentation {
        private boolean throwOnKeyEvents = false;

        @Override
        public void runOnMainSync(Runnable runner) {
            runner.run();
        }
        
        @Override
        public void sendKeyDownUpSync(int key) {
            if( throwOnKeyEvents ) {
                throw new SecurityException();
            }
            
            super.sendKeyDownUpSync(key);
        }

        public void setToThrowOnKeyEvents() {
            throwOnKeyEvents = true;
        }
    }

}
