package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.view.*;
import android.widget.*;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(Enclosed.class)
public class ListItemFinderTest {
    
    @RunWith(BrazenheadTestRunner.class)
    public static class FindingByText {
        @Mock Solo solo;
        @Mock TextView theFoundText;
        
        private ListItemFinder listItemFinder;
        
        @Before
        public void setUp() {
            TestRunInformation.setSolo(solo);
            when(solo.waitForText(anyString()))
                .thenReturn(true);
            listItemFinder = new ListItemFinder();
        }
        
        @Test
        public void itIndicatesIfNoTextIsFound() {
            when(solo.waitForText(anyString())).thenReturn(false);
            assertThat(listItemFinder.findByText("To find"), is(nullValue()));
            verify(solo).waitForText("To find");
        }
        
        @Test
        public void itSelectsTheFoundText() {
            when(solo.getText("To find")).thenReturn(theFoundText);
            listItemFinder.findByText("To find");
            verify(solo).getText("To find");
        }
        
        @Test
        public void itCanReturnTheParentListItem() {
            final View listItemView = mock(RelativeLayout.class);
            final ListView theListView = mock(ListView.class);
            when(solo.getText("To find")).thenReturn(theFoundText);
            when(theFoundText.getParent()).thenReturn((ViewParent) listItemView);
            when(listItemView.getParent()).thenReturn(theListView);
            
            assertThat(listItemFinder.findByText("To find"), is(listItemView));
        }
    }

}
