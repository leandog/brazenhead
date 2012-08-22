package com.leandog.gametel.driver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

public class GametelRequestHandler extends AbstractHandler {

    private final GametelServer gametelServer;

    public GametelRequestHandler(GametelServer gametelServer) {
        this.gametelServer = gametelServer;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
        if (isKillCommand(request)) {
            stopServer(response);
            return;
        }
        
        getCommands(request);
        
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{hello: 'World'}");
        setHandled(request);

    }

    private String getCommands(HttpServletRequest request) {
        final String commandsParameter = request.getParameter("commands");
        if( null == commandsParameter ) {
            throw new IllegalArgumentException("gametel-driver requires a \"commands\" parameter");
        }
        return commandsParameter;
    }

    private boolean isKillCommand(HttpServletRequest request) {
        return request.getPathInfo().equals("/kill");
    }

    private void stopServer(HttpServletResponse response) {
        try {
            response.flushBuffer();
            this.gametelServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHandled(HttpServletRequest request) {
        ((Request) request).setHandled(true);
    }

}