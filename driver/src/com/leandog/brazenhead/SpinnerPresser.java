package com.leandog.brazenhead;

import android.app.Instrumentation;
import android.view.KeyEvent;
import android.widget.Spinner;

import com.robotium.solo.BrazenheadSleeper;
import com.robotium.solo.Solo;

public class SpinnerPresser {

    private final BrazenheadSleeper sleeper;
    private final Instrumentation instrumentation;

    public SpinnerPresser(final Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
        sleeper = new BrazenheadSleeper();
    }

    public SpinnerPresser(final Instrumentation instrumentation, final BrazenheadSleeper sleeper) {
        this.instrumentation = instrumentation;
        this.sleeper = sleeper;
    }

    public void pressSpinnerItemById(int spinnerId, int itemIndex) {
        pressSpinnerItem((Spinner) solo().getView(spinnerId), itemIndex);
    }

    public void pressSpinnerItem(final Spinner spinner, int itemIndex) {
        final int direction = whichDirection(itemIndex);
        final int numberOfMoves = Math.abs(itemIndex);
        
        setTheInitialFocus(spinner);

        for (int moves = 0; moves < numberOfMoves; moves++) {
            sleeper.sleepMini();
            move(direction);
        }

        selectTheItem();
    }

    private void selectTheItem() {
        sendKey(KeyEvent.KEYCODE_ENTER);
    }

    private int whichDirection(int itemIndex) {
        return itemIndex < 0 ? KeyEvent.KEYCODE_DPAD_UP : KeyEvent.KEYCODE_DPAD_DOWN;
    }

    private void move(int direction) {
        sendKey(direction);
    }

    private void setTheInitialFocus(final Spinner spinner) {
        solo().clickOnView(spinner);
        sleeper.sleep();
        sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
    }

    private void sendKey(int key) {
        try {
            instrumentation.sendKeyDownUpSync(key);
        } catch (SecurityException ignored) {
        }
    }

    private Solo solo() {
        return TestRunInformation.getSolo();
    }

}
