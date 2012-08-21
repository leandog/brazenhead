package com.leandog.gametel.driver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.isA;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.bio.SocketConnector;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.server.JettyServer;
import com.leandog.gametel.driver.test.GametelTestRunner;

@RunWith(GametelTestRunner.class)
public class GametelServerTest {

    @Mock
    Solo solo;
    @Mock
    JettyServer server;

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

    @Test
    public void itUsesASocketConnector() throws Exception {
        gametelServer.start();
        verify(server).addConnector(isA(SocketConnector.class));
    }

    @Test
    public void theConnectorListensOnPort54767() throws Exception {
        gametelServer.start();

        ArgumentCaptor<SocketConnector> theConnector = ArgumentCaptor.forClass(SocketConnector.class);
        verify(server).addConnector(theConnector.capture());

        assertThat(theConnector.getValue().getPort(), is(54767));
    }

    @Test
    public void itOnlyConfiguresASingleAcceptorPort() throws Exception {
        gametelServer.start();

        ArgumentCaptor<SocketConnector> theConnector = ArgumentCaptor.forClass(SocketConnector.class);
        verify(server).addConnector(theConnector.capture());

        assertThat(theConnector.getValue().getAcceptors(), is(1));
    }

    @Test
    public void itCanWaitUntilTheServerIsFinished() throws Exception {
        gametelServer.waitUntilFinished();
        verify(server).join();
    }
    
    @Test
    public void itCanBeStopped() throws Exception {
        gametelServer.stop();
        verify(server).stop();
    }

}
