package com.leandog.brazenhead;

import android.app.Instrumentation;
import android.view.KeyEvent;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.*;

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
        solo().clickOnView(spinner);
        sleeper.sleep();
        try {
            instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
        } catch (SecurityException ignored) {
        }
        boolean countingUp = true;
        if (itemIndex < 0) {
            countingUp = false;
            itemIndex *= -1;
        }
        for (int i = 0; i < itemIndex; i++) {
            sleeper.sleepMini();
            if (countingUp) {
                try {
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
                } catch (SecurityException ignored) {
                }
            } else {
                try {
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
                } catch (SecurityException ignored) {
                }
            }
        }
        try {
            instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
        } catch (SecurityException ignored) {
        }
    }

    private Solo solo() {
        return TestRunInformation.getSolo();
    }

}
