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
        
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{hello: 'World'}");
        ((Request) request).setHandled(true);

        if (request.getPathInfo().equals("/kill")) {
            try {
                response.flushBuffer();
                this.gametelServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}