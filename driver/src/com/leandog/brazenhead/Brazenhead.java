package com.leandog.brazenhead;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.view.View;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.BrazenheadSleeper;
import com.jayway.android.robotium.solo.By;
import com.jayway.android.robotium.solo.Solo;
import com.jayway.android.robotium.solo.WebElement;

public class Brazenhead {

    private final SpinnerPresser spinnerPresser;
    private final ListItemFinder listItemFinder;
    private ListItemPresser listItemPresser;

    public Brazenhead(final Instrumentation instrumentation, final Solo solo) {
        this.spinnerPresser = new SpinnerPresser(instrumentation);
        this.listItemFinder = new ListItemFinder(instrumentation, solo, new BrazenheadSleeper());
        this.listItemPresser = new ListItemPresser(solo, listItemFinder);
    }

    public Brazenhead(final SpinnerPresser spinnerPresser, final ListItemFinder listItemFinder, final ListItemPresser listItemPresser) {
        this.spinnerPresser = spinnerPresser;
        this.listItemFinder = listItemFinder;
        this.listItemPresser = listItemPresser;
    }

    public int idFromName(final String namedId) {
        return getResources().getIdentifier(namedId, "id", targetPackage());
    }

    public void pressSpinnerItem(final Spinner spinner, int itemIndex) {
        spinnerPresser.pressSpinnerItem(spinner, itemIndex);
    }

    public void pressSpinnerItemById(int spinnerId, int itemIndex) {
        spinnerPresser.pressSpinnerItemById(spinnerId, itemIndex);
    }

    public View listItemByText(final String itemText) throws Exception {
        return listItemFinder.findByText(itemText);
    }
    
    public View listItemByIndex(final int itemIndex) {
        return listItemFinder.findByIndex(itemIndex);
    }
    
    public View listItemByIndex(final int itemIndex, final int whichList) {
        return listItemFinder.findByIndex(itemIndex, whichList);
    }

    public void pressListItemByIndex(final int itemIndex) {
        listItemPresser.pressListItem(itemIndex);
    }

    public void pressListItemByIndex(final int itemIndex, final int whichList) {
        listItemPresser.pressListItem(itemIndex, whichList);
    }
    
    public ArrayList<WebElement> getWebViewsBy(final String how, final String what) {
    	try {
			final By byHow =  (By) By.class.getDeclaredMethod(how, String.class).invoke(null, what);
			return TestRunInformation.getSolo().getCurrentWebElements(byHow);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Unable to locate a WebView by \"%s\"", how));
		}
    }

    private Activity theCurrentActivity() {
        return TestRunInformation.getSolo().getCurrentActivity();
    }

    private Resources getResources() {
        return theCurrentActivity().getResources();
    }

    private String targetPackage() {
        return theCurrentActivity().getPackageName();
    }

}
