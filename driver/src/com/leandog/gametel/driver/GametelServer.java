package com.leandog.gametel.driver;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.bio.SocketConnector;

import android.util.Log;

import com.leandog.gametel.driver.server.JettyServer;

public class GametelServer {
    JettyServer server;

    public GametelServer(final JettyServer server) {
        this.server = server;
    }

    public void start() throws Exception {
        server.setHandler(new GametelRequestHandler(this));
        server.addConnector(createConnector());
        server.start();
        log("Started the server..");
    }

    private Connector createConnector() {
        SocketConnector socketConnector = new SocketConnector();
        socketConnector.setPort(54767);
        socketConnector.setAcceptors(1);
        return socketConnector;
    }

    void log(final String message, final Object... args) {
        Log.v(getClass().getName(), String.format(message, args));
    }

    public void waitUntilFinished() throws InterruptedException {
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

}
