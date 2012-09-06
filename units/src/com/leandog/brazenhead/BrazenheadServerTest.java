package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mortbay.jetty.bio.SocketConnector;

import com.leandog.brazenhead.server.JettyServer;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class BrazenheadServerTest {

    @Mock JettyServer server;

    private BrazenheadServer brazenheadServer;

    @Before
    public void setUp() {
        brazenheadServer = new BrazenheadServer(server);
    }

    @Test
    public void itUsesTheBrazenheadRequestHandler() throws Exception {
        brazenheadServer.start();
        verify(server).setHandler(isA(BrazenheadRequestHandler.class));
    }

    @Test
    public void itUsesASocketConnector() throws Exception {
        brazenheadServer.start();
        verify(server).addConnector(isA(SocketConnector.class));
    }

    @Test
    public void theConnectorListensOnPort54767() throws Exception {
        brazenheadServer.start();

        ArgumentCaptor<SocketConnector> theConnector = ArgumentCaptor.forClass(SocketConnector.class);
        verify(server).addConnector(theConnector.capture());

        assertThat(theConnector.getValue().getPort(), is(54767));
    }

    @Test
    public void itOnlyConfiguresASingleAcceptorPort() throws Exception {
        brazenheadServer.start();

        ArgumentCaptor<SocketConnector> theConnector = ArgumentCaptor.forClass(SocketConnector.class);
        verify(server).addConnector(theConnector.capture());

        assertThat(theConnector.getValue().getAcceptors(), is(1));
    }

    @Test
    public void itCanWaitUntilTheServerIsFinished() throws Exception {
        brazenheadServer.waitUntilFinished();
        verify(server).join();
    }
    
    @Test
    public void itCanBeStopped() throws Exception {
        brazenheadServer.stop();
        verify(server).stop();
    }

}
