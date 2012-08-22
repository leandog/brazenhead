package com.leandog.gametel.driver;

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

public class GametelRequestHandlerTest {
    
    @Mock GametelServer gametelServer;
    @Mock Request request;
    @Mock HttpServletResponse response;
    @Mock PrintWriter responseWriter;
    
    private GametelRequestHandler handler;
    
    @Before
    public void setUp() throws IOException {
        initMocks();
        handler = new GametelRequestHandler(gametelServer);
    }
    
    @Test
    public void itStopsTheServerWhenAskedTo() throws Exception {
        when(request.getPathInfo()).thenReturn("/kill");
        handle();
        verify(gametelServer).stop();
    }

    private void initMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(response.getWriter()).thenReturn(responseWriter);
    }

    private void handle() {
        try {
            handler.handle(null, request, response, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
