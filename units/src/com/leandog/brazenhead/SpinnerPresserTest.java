package com.leandog.brazenhead;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.app.Instrumentation;
import android.view.KeyEvent;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.*;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(Enclosed.class)
public class SpinnerPresserTest {

    @RunWith(BrazenheadTestRunner.class)
    public static class PressingSpinnerItemsByView {

        @Mock Solo solo;
        @Mock Instrumentation instrumentation;
        @Mock BrazenheadSleeper sleeper;
        @Mock Spinner spinner;
        
        private final int INITIAL = 1;
        
        private SpinnerPresser spinnerPresser;

        @Before
        public void setUp() {
            TestRunInformation.setSolo(solo);
            spinnerPresser = new SpinnerPresser(instrumentation, sleeper);
        }
        
        @Test
        public void itClicksOnTheFoundSpinnerAndSleepsFirst() {
            spinnerPresser.pressSpinnerItem(spinner, 1);
            verify(solo).clickOnView(spinner);
            verify(sleeper).sleep();
        }
        
        @Test
        public void itBringsTheFocusIntoTheViewInitially() {
            spinnerPresser.pressSpinnerItem(spinner, 0);
            verify(instrumentation).sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
        }
        
        @Test
        public void itCanMoveDownInTheSpinner() {
            spinnerPresser.pressSpinnerItem(spinner, 4);
            verify(instrumentation, times(INITIAL + 4)).sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
        }
        
        @Test
        public void itCanMoveUpInTheSpinner() {
            spinnerPresser.pressSpinnerItem(spinner, -4);
            verify(instrumentation, times(4)).sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
        }
        
        @Test
        public void itSelectsTheItemWhenFinished() {
            spinnerPresser.pressSpinnerItem(spinner, 1);
            verify(instrumentation).sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
        }
        
        @Test
        public void itSleepsForATinyBitBetweenEachMovement() {
            spinnerPresser.pressSpinnerItem(spinner, 7);
            verify(sleeper, times(7)).sleepMini();
        }
        
    }
    
    @RunWith(BrazenheadTestRunner.class)
    public static class PressingSpinnerItemsById {
        @Mock Solo solo;
        @Mock Instrumentation instrumentation;
        @Mock BrazenheadSleeper sleeper;
        @Mock Spinner spinner;
        
        private SpinnerPresser spinnerPresser;

        @Before
        public void setUp() {
            TestRunInformation.setSolo(solo);
            spinnerPresser = new SpinnerPresser(instrumentation, sleeper);
        }
        
        @Test
        public void itFindsTheSpinnerAndCallsTheViewOverload() {
            when(solo.getView(123)).thenReturn(spinner);
            spinnerPresser.pressSpinnerItemById(123, -3);
            
            verify(solo).clickOnView(spinner);
            verify(instrumentation, times(3)).sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
            verify(instrumentation).sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
        }
    }
}
