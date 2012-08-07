package com.example.gametel_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.AbstractHandler;

import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;

public class ServerInstrumentation extends Instrumentation {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        start();
    }

    @Override
    public void onStart() {
        final Server server = new Server();
        try {
            server.setHandler(new HelloWorld());
            server.addConnector(createConnector());
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connector createConnector() {
        SocketConnector socketConnector = new SocketConnector();
        socketConnector.setPort(54767);
        socketConnector.setAcceptors(1);
        return socketConnector;
    }

    public class HelloWorld extends AbstractHandler {

        @Override
        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int arg3) throws IOException, ServletException {
            
            log("ContentLength = %d", request.getContentLength());
            log("ContentType = %s", request.getContentType());
            
            final StringBuilder stringBuilder = new StringBuilder();
            final BufferedReader reader = request.getReader();
            
            String line = null;
            while( null != (line = reader.readLine())) {
                stringBuilder.append(line);
            }
            
            log("Content = \n\n%s", stringBuilder.toString());
            
            
            final Map params = request.getParameterMap();
            for (final Object key : params.keySet()) {
                log("%s = %s", key, params.get(key).toString());
            }
            
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("{title: 'Hello World', sender: 'Android'}");
            log("I just invoked this from my browser on my computer.");
            ((Request)request).setHandled(true);
        }

    }

    private void log(final String message, final Object... args) {
        Log.v(getClass().getName(), String.format(message, args));
    }

}
