package com.leandog.gametel.driver.commands;

import static org.mockito.Mockito.verify;

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

    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        TestRunInformation.setSolo(solo);
    }
}
