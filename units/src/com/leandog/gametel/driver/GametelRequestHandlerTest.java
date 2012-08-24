package com.leandog.gametel.driver;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mortbay.jetty.Request;

import com.google.gson.Gson;
import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.commands.Command;
import com.leandog.gametel.driver.commands.CommandRunner;

public class GametelRequestHandlerTest {
    
    @Mock GametelServer gametelServer;
    @Mock Request request;
    @Mock HttpServletResponse response;
    @Mock PrintWriter responseWriter;
    @Mock Solo solo;
    @Spy CommandRunner commandRunner;
    
    private GametelRequestHandler handler;
    private Command defaultCommand = new Command("scrollDown");
    
    @Before
    public void setUp() throws IOException {
        initMocks();
        handler = new GametelRequestHandler(gametelServer, commandRunner);
    }
    
    @Test(expected = RuntimeException.class)
    public void itRequiresACommandsParameter() {
        when(request.getParameter("commands")).thenReturn(null);
        handle();
    }
    
    @Test
    public void itStopsTheServerWhenAskedTo() throws Exception {
        when(request.getPathInfo()).thenReturn("/kill");
        handle();
        verify(gametelServer).stop();
    }
    
    @Test
    public void itIndicatesTheRequestWasHandled() {
        handle();
        verify(request).setHandled(true);
    }
    
    @Test
    public void itCanInvokeCommands() {
        post(defaultCommand, defaultCommand);
        verify(commandRunner, times(2)).execute((Command)any());
    }

    @Test
    public void itCanReturnPrimitiveResults() {
        when(commandRunner.theLastResult()).thenReturn(1234);
        post(new Command("scrollDown"));
        verify(responseWriter).print("1234");
    }
    
    @Test
    public void itCanInvokeCommandsWithIntegers() {
        post(new Command("clickInList", 7));
        verify(solo).clickInList(7);
    }
    
    @Test
    public void itCanInvokeCommandsWithFloats() {
        post(new Command("clickLongOnScreen", 1.23f, 4.56f));
        verify(solo).clickLongOnScreen(1.23f, 4.56f);
    }
    
    @Test
    public void itCanInvokeCommandsWithStrings() {
        post(new Command("clickOnMenuItem", "something"));
        verify(solo).clickOnMenuItem("something");
    }

    private void initMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(response.getWriter()).thenReturn(responseWriter);
        when(request.getParameter("commands")).thenReturn("[]");
        when(request.getPathInfo()).thenReturn("/");
        TestRunInformation.setSolo(solo);
    }

    private void handle() {
        try {
            handler.handle(null, request, response, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void post(Command... commands) {
        when(request.getParameter("commands"))
            .thenReturn(jsonCommands(commands));
        handle();
    }

    private String jsonCommands(final Command... commands) {
        return new Gson().toJson(commands);
    }
}
