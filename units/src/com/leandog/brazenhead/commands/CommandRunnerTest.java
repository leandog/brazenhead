package com.leandog.brazenhead.commands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.*;
import org.mockito.*;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.brazenhead.*;
import com.leandog.brazenhead.commands.Command.Target;

public class CommandRunnerTest {

    @Mock Solo solo;
    @Mock Brazenhead brazenhead;
    CommandRunner commandRunner;

    @Before
    public void setUp() {
        initMocks();
        commandRunner = new CommandRunner();
    }

    @Test
    public void itCanInvokeParameterless() throws Exception {
        commandRunner.execute(new Command("goBack"));
        verify(solo).goBack();
    }

    @Test
    public void itCanGiveTheLastResult() throws Exception {
        when(solo.scrollDown()).thenReturn(true);
        commandRunner.execute(new Command("scrollDown"));
        assertThat(commandRunner.theLastResult(), equalTo((Object) true));
    }

    @Test
    public void itCanInvokeMethodsTakingIntegers() throws Exception {
        commandRunner.execute(new Command("clearEditText", 3));
        verify(solo).clearEditText(3);
    }

    @Test
    public void itCanInvokeMethodsTakingFloats() throws Exception {
        commandRunner.execute(new Command("clickLongOnScreen", 1.0f, 2.0f));
        verify(solo).clickLongOnScreen(1.0f, 2.0f);
    }

    @Test
    public void itCanInvokeMethodsTakingAString() throws Exception {
        commandRunner.execute(new Command("clickLongOnText", "someText"));
        verify(solo).clickLongOnText("someText");
    }
    
    @Test
    public void itCanChainMethodCallsFromTheLastResult() throws Exception {
        commandRunner.execute(new Command("getClass"), new Command("getName"));
        assertThat(commandRunner.theLastResult(), equalTo((Object)solo.getClass().getName()));
    }

    @Test
    public void itCanInvokeMethodsTakingABoolean() throws Exception {
        commandRunner.execute(new Command("clickOnText", "someText", 123, true));
        verify(solo).clickOnText("someText", 123, true);
    }
    
    @Test
    public void itClearsTheLastResultBeforeExecutingAgain() throws Exception {
        commandRunner.execute(new Command("clickInList", 0));
        commandRunner.execute(new Command("clickInList", 0));
        verify(solo, times(2)).clickInList(0);
    }
    
    @Test
    public void itCanDesignateTheRobotiumTarget() throws Exception {
        commandRunner.execute(new Command("clickInList", Target.Robotium, 0), new Command("clickInList", Target.Robotium, 0));
        verify(solo, times(2)).clickInList(0);
    }
    
    @Test
    public void itCanDesignateTheBrazenheadTarget() throws Exception {
        commandRunner.execute(new Command("getInstrumentation", Target.Brazenhead));
        verify(brazenhead).getInstrumentation();
    }
    
    @Test
    public void itCaptureVariables() throws Exception {
        final Command command = new Command("clickInList", 0);
        command.setVariable("@@some_variable@@");
        
        commandRunner.execute(command);
        
        assertThat(commandRunner.variables.get("@@some_variable@@"), instanceOf(ArrayList.class));
    }
    
    @Test
    public void itCanUseVariablesLater() throws Exception {
        final Command firstCommand = new Command("isCheckBoxChecked", 0);
        firstCommand.setVariable("@@some_variable@@");
        
        Command nextCommand = new Command("clickOnMenuItem", Target.Robotium, "text", "@@some_variable@@");
        
        commandRunner.execute(firstCommand, nextCommand);
        
        verify(solo).isCheckBoxChecked(0);
        verify(solo).clickOnMenuItem("text", false);
    }
    
    @Test
    public void itClearsVariablesEachExecution() throws Exception {
        final Command firstCommand = new Command("isCheckBoxChecked", 0);
        firstCommand.setVariable("@@some_variable@@");
        
        commandRunner.execute(firstCommand);
        commandRunner.execute();
        
        assertThat(commandRunner.variables.isEmpty(), is(true));
    }

    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        TestRunInformation.setSolo(solo);
        TestRunInformation.setBrazenhead(brazenhead);
    }
    
}
