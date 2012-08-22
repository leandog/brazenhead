package com.leandog.gametel.driver.commands;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.TestRunInformation;

@RunWith(Enclosed.class)
public class CommandRunnerTest {

    public static class ExecutingAgainstRobotium {
        @Mock
        Solo solo;
        private CommandRunner commandRunner;

        @Before
        public void setUp() {
            initMocks();
            commandRunner = new CommandRunner();
        }

        @Test
        public void itCanInvokeParameterless() {
            commandRunner.execute(new Command("goBack"));
            verify(solo).goBack();
        }

        @Test
        public void itCanGiveTheLastResult() {
            when(solo.scrollDown()).thenReturn(true);
            commandRunner.execute(new Command("scrollDown"));
            assertThat(commandRunner.theLastResult(), equalTo((Object) true));
        }

        @Test
        public void itCanInvokeMethodsTakingIntegers() {
            commandRunner.execute(new Command("clearEditText", 3));
            verify(solo).clearEditText(3);
        }
        
        @Test
        public void itCanInvokeMethodsTakingFloats() {
            commandRunner.execute(new Command("clickLongOnScreen", 1.0f, 2.0f));
            verify(solo).clickLongOnScreen(1.0f, 2.0f);
        }

        private void initMocks() {
            MockitoAnnotations.initMocks(this);
            TestRunInformation.setSolo(solo);
        }
    }
}
