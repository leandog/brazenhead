package com.leandog.gametel.driver;

import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.isA;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.server.JettyServer;
import com.leandog.gametel.driver.test.GametelTestRunner;

@RunWith(GametelTestRunner.class)
public class GametelServerTest {
    
    @Mock Solo solo;
    @Mock JettyServer server;
    
    private GametelServer gametelServer;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gametelServer = new GametelServer(solo, server);
    }
    
    @Test
    public void itUsesTheGametelRequestHandler() throws Exception {
        gametelServer.start();
        verify(server).setHandler(isA(GametelRequestHandler.class));
    }
}
