package com.leandog.gametel.driver;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mortbay.jetty.Request;

import com.google.gson.Gson;
import com.leandog.gametel.driver.commands.Command;
import com.leandog.gametel.driver.commands.CommandRunner;

public class GametelRequestHandlerTest {
    
    @Mock GametelServer gametelServer;
    @Mock Request request;
    @Mock HttpServletResponse response;
    @Mock PrintWriter responseWriter;
    @Mock CommandRunner commandRunner;
    
    private GametelRequestHandler handler;
    
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
        when(request.getParameter("commands"))
            .thenReturn(jsonCommands(new Command(), new Command()));
        handle();
        verify(commandRunner, times(2)).execute((Command)any());
    }

    private void initMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(response.getWriter()).thenReturn(responseWriter);
        when(request.getParameter("commands")).thenReturn("[]");
        when(request.getPathInfo()).thenReturn("/");
    }

    private void handle() {
        try {
            handler.handle(null, request, response, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String jsonCommands(final Command... commands) {
        return new Gson().toJson(commands);
    }
}
