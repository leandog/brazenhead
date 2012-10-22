package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.view.*;
import android.widget.*;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.exceptions.IsNotAListViewItem;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(Enclosed.class)
public class ListItemFinderTest {

    @RunWith(BrazenheadTestRunner.class)
    public static class FindingByText {
        @Mock
        Solo solo;
        @Mock
        TextView theFoundText;

        private ListItemFinder listItemFinder;

        @Before
        public void setUp() {
            TestRunInformation.setSolo(solo);
            when(solo.waitForText(anyString())).thenReturn(true);
            listItemFinder = new ListItemFinder();
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
