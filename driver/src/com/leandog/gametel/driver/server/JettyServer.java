package com.leandog.gametel.driver.server;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

public class JettyServer {
    
    final private Server server;

    public JettyServer() {
        this.server = new Server();
    }

    public void setHandler(AbstractHandler handler) {
        server.setHandler(handler);
    }

    public void addConnector(Connector createConnector) {
        server.addConnector(createConnector);
    }

    public void start() throws Exception {
        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

}
