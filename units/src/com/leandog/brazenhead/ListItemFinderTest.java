package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.*;

import android.app.Instrumentation;
import android.view.*;
import android.widget.*;

import com.jayway.android.robotium.solo.*;
import com.leandog.brazenhead.exceptions.IsNotAListViewItem;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(Enclosed.class)
public class ListItemFinderTest {

    @RunWith(BrazenheadTestRunner.class)
    public static class FindingByIndex {
        @Spy InstrumentationStub instrumentation = new InstrumentationStub();
        @Mock ArrayList<ListView> theLists;
        @Mock ListView theFirstList;
        @Mock ListView theSecondList;
        @Mock Solo solo;
        @Mock BrazenheadSleeper sleeper;

        private ListItemFinder listItemFinder;

        @Before
        public void setUp() {
            initMocks();
            listItemFinder = new ListItemFinder(instrumentation, solo, sleeper);
        }

        @Test
        public void itBringsTheListViewIntoFocusInitially() {
            listItemFinder.findByIndex(0);
            verify(solo, atLeastOnce()).waitForView(ListView.class);
            verify(theFirstList).requestFocus();
            verify(sleeper).sleepMini();
            verify(instrumentation).sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
        }

        @Test
        public void itUsesTheFirstListByDefault() {
            listItemFinder.findByIndex(7);
            verify(solo, atLeastOnce()).getCurrentViews(ListView.class);
            verify(theLists, atLeastOnce()).get(0);
        }

        @Test
        public void itCanUseAnAlternateList() {
            listItemFinder.findByIndex(7, 1);
            verify(solo, atLeastOnce()).getCurrentViews(ListView.class);
            verify(theLists, atLeastOnce()).get(1);
        }

        @Test
        public void selectingListItemsAreOneBaseIndexed() {
            listItemFinder.findByIndex(7);
            verify(theFirstList).setSelection(6);
        }

        @Test
        public void listSelectionsAreDoneOnTheUiThread() {
            listItemFinder.findByIndex(7);
            verify(instrumentation).runOnMainSync(any(Runnable.class));
        }

        @Test
        public void theFirstItemIsBothIndexZeroAndOne() {
            listItemFinder.findByIndex(0);
            verify(theFirstList).setSelection(0);
        }

        @Test
        public void itCanHandleInjectKeyEventExceptions() {
            instrumentation.setToThrowOnKeyEvents();
            listItemFinder.findByIndex(7);
            assertThat("We should have gotten here", is(notNullValue()));
        }

        private void initMocks() {
            when(solo.getCurrentViews(ListView.class)).thenReturn(theLists);
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
                if (throwOnKeyEvents) {
                    throw new SecurityException();
                }

                super.sendKeyDownUpSync(key);
            }

            public void setToThrowOnKeyEvents() {
                throwOnKeyEvents = true;
            }
        }
    }

    @RunWith(BrazenheadTestRunner.class)
    public static class FindingByText {
        @Mock Instrumentation instrumentation;
        @Mock Solo solo;
        @Mock TextView theFoundText;
        @Mock BrazenheadSleeper sleeper;

        private ListItemFinder listItemFinder;

        @Before
        public void setUp() {
            TestRunInformation.setSolo(solo);
            when(solo.waitForText(anyString())).thenReturn(true);
            listItemFinder = new ListItemFinder(instrumentation, solo, sleeper);
        }

        @Test
        public void itIndicatesIfNoTextIsFound() throws IsNotAListViewItem {
            when(solo.waitForText(anyString())).thenReturn(false);
            try {
                listItemFinder.findByText("To find");
                fail("Should have raised an exception!");
            } catch (Exception e) {
                verify(solo).waitForText("To find");
                assertThat(e.getMessage(), is("A View with text \"To find\" was not found"));
            }
        }

        @Test
        public void itSelectsTheFoundText() throws Exception {
            setViewHierarchy("To find", ListView.class);
            listItemFinder.findByText("To find");
            verify(solo).getText("To find");
        }

        @Test
        public void itCanReturnTheParentListItem() throws Exception {
            setViewHierarchy("To find", RelativeLayout.class, ListView.class);
            assertThat(listItemFinder.findByText("To find"), is(instanceOf(RelativeLayout.class)));
        }

        @Test
        public void theFoundItemCanBeTheParent() throws Exception {
            setViewHierarchy("To find", ListView.class);
            assertThat(listItemFinder.findByText("To find"), is(instanceOf(TextView.class)));
        }

        @Test(expected = IsNotAListViewItem.class)
        public void itIndicatesIfItIsNotPartOfAList() throws Exception {
            setViewHierarchy("To find", RelativeLayout.class, LinearLayout.class);
            assertThat(listItemFinder.findByText("To find"), is(nullValue()));
        }

        private void setViewHierarchy(final String toFind, final Class<?>... viewTypes) {
            when(solo.getText(toFind)).thenReturn(theFoundText);

            View theLastView = theFoundText;
            for (final Class<?> viewType : viewTypes) {
                final View nextView = (View) mock(viewType);
                when(theLastView.getParent()).thenReturn((ViewParent) nextView);
                theLastView = nextView;
            }
        }
    }

}
