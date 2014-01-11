package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.Spinner;

import com.leandog.brazenhead.test.BrazenheadTestRunner;
import com.robotium.solo.By;
import com.robotium.solo.Solo;

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
    
    @Test
	public void itCanFindWebViewsByVariousProperties() {
		final HashMap<String, String> hows = new HashMap<String, String>();
		hows.put("id", "Id");
		hows.put("xpath", "Xpath");
		hows.put("cssSelector", "CssSelector");
		hows.put("name", "Name");
		hows.put("className", "ClassName");
		hows.put("textContent", "Text");
		hows.put("tagName", "TagName");
		
		for(final Map.Entry<String, String> how : hows.entrySet()) {
			brazenhead.getWebViewsBy(how.getKey(), "does not matter");
			
			final ArgumentCaptor<By> byArgument = ArgumentCaptor.forClass(By.class);
			verify(solo).getCurrentWebElements(byArgument.capture());
			assertThat(byArgument.getValue().getClass().getName(), is(by(how.getValue())));
			
			reset(solo);
		}

	}
    
    @Test
    public void itCanFindWebViewsByName() {
    	brazenhead.getWebViewsBy("name", "blar");
    	
    	final ArgumentCaptor<By> byArgument = ArgumentCaptor.forClass(By.class);
    	verify(solo).getCurrentWebElements(byArgument.capture());
    	assertThat(byArgument.getValue().getClass().getName(), is(by("Name")));
    }

	private void initMocks() {
        TestRunInformation.setSolo(solo);
        when(solo.getCurrentActivity()).thenReturn(activity);
        when(activity.getResources()).thenReturn(resources);
    }

    private String by(final String how) {
    	return String.format("com.robotium.solo.By$%s", how);
	}

}
