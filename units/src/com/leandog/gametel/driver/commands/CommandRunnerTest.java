package com.leandog.gametel.driver.commands;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.TestRunInformation;

public class CommandRunnerTest {
    
    @Mock Solo solo;
    
    private CommandRunner commandRunner;
    
    @Before
    public void setUp() {
        initMocks();
        commandRunner = new CommandRunner();
    }
    
    @Test
    public void itCanInvokeParameterlessSoloMethod() {
        commandRunner.execute(new Command("goBack"));
        verify(solo).goBack();
    }
    
    @Test
    public void itCanGiveTheLastResult() {
        when(solo.scrollDown()).thenReturn(true);
        commandRunner.execute(new Command("scrollDown"));
        assertThat(commandRunner.theLastResult(), equalTo((Object)true));
    }
    
    @Test
    public void itCanCallSoloMethodsThatTakeIntegers() {
        commandRunner.execute(new Command("clearEditText", 3));
        verify(solo).clearEditText(3);
    }

    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        TestRunInformation.setSolo(solo);
    }
}
