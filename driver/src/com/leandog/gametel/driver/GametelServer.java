package com.leandog.gametel.driver;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;

import android.util.Log;

import com.jayway.android.robotium.solo.Solo;

public class GametelServer {
    Server server;

    public GametelServer(final Solo solo) {
    }

    public void start() throws Exception {
        server = new Server();
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
