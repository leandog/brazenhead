package com.example.gametel_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.AbstractHandler;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.jayway.android.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public class TheTest extends ActivityInstrumentationTestCase2 {

    private static Class<?> launcherActivityClass;
    static {
        try {
            launcherActivityClass = Class.forName(TestRunInformation.getFullLauncherName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public TheTest() throws ClassNotFoundException {
        super(TestRunInformation.getPackageName(), launcherActivityClass);
    }

    private Solo solo;
    private Server server;

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testCanOpenSettings() {
        server = new Server();
        try {
            server.setHandler(new HelloWorld());
            server.addConnector(createConnector());
            server.start();
            log("Started the server..");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log("Finished running the test.");

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

            log("Request Path:  %s", request.getPathInfo());

            log("ContentLength = %d", request.getContentLength());
            log("ContentType = %s", request.getContentType());

            final StringBuilder stringBuilder = new StringBuilder();
            final BufferedReader reader = request.getReader();

            String line = null;
            while (null != (line = reader.readLine())) {
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
            ((Request) request).setHandled(true);

            if (request.getPathInfo().equals("/kill")) {
                try {
                    response.flushBuffer();
                    server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void log(final String message, final Object... args) {
        Log.v(getClass().getName(), String.format(message, args));
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();

    }
}
