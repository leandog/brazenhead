package com.leandog.gametel.driver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.io.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mortbay.jetty.*;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.jayway.android.robotium.solo.Solo;
import com.leandog.gametel.driver.commands.*;
import com.leandog.gametel.driver.exceptions.CommandNotFoundException;
import com.leandog.gametel.driver.test.GametelTestRunner;
import com.leandog.gametel.json.ExceptionJsonSerializer.ExceptionSummary;

@RunWith(GametelTestRunner.class)
public class GametelRequestHandlerTest {

    @Mock GametelServer gametelServer;
    @Mock Request request;
    @Mock Response response;
    @Mock PrintWriter responseWriter;
    @Mock Solo solo;
    @Mock Context context;
    @Spy CommandRunner commandRunner;

    private GametelRequestHandler handler;

    @Before
    public void setUp() throws IOException {
        initMocks();
        handler = new GametelRequestHandler(gametelServer, commandRunner);
    }

    @Test
    public void itRequiresACommandsParameter() throws Exception {
        when(request.getParameter("commands")).thenReturn(null);
        handle();
        verify(response).setStatus(Response.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void itStopsTheServerWhenAskedTo() throws Exception {
        when(request.getPathInfo()).thenReturn("/kill");
        handle();
        verify(gametelServer).stop();
    }

    @Test
    public void itIndicatesTheRequestWasHandled() throws Exception {
        handle();
        verify(request).setHandled(true);
    }

    @Test
    public void itCanInvokeCommands() throws Exception {
        Command firstCommand = new Command("scrollDown");
        Command lastCommand = new Command("booleanValue");
        post(firstCommand, lastCommand);
        verify(commandRunner).execute(firstCommand, lastCommand);
    }

    @Test
    public void itCanReturnPrimitiveResults() throws Exception {
        when(commandRunner.theLastResult()).thenReturn(1234);
        post(new Command("scrollDown"));
        verify(responseWriter).print("1234");
    }

    @Test
    public void itCanReturnViewResultsAsJson() throws Exception {
        when(commandRunner.theLastResult()).thenReturn(new View(context));

        post(new Command("getCurrentViews"));

        ArgumentCaptor<String> jsonArg = ArgumentCaptor.forClass(String.class);
        verify(responseWriter).print(jsonArg.capture());
        assertThat(jsonArg.getValue().matches("\\{.*\\}"), is(true));
    }

    @Test
    public void itCanInvokeCommandsWithIntegers() throws Exception {
        post(new Command("clickInList", 7));
        verify(solo).clickInList(7);
    }

    @Test
    public void itCanInvokeCommandsWithFloats() throws Exception {
        post(new Command("clickLongOnScreen", 1.23f, 4.56f));
        verify(solo).clickLongOnScreen(1.23f, 4.56f);
    }

    @Test
    public void itCanInvokeCommandsWithStrings() throws Exception {
        post(new Command("clickOnMenuItem", "something"));
        verify(solo).clickOnMenuItem("something");
    }

    @Test
    public void itCanHandleErrorsThrownFromCommands() throws Exception {
        post(new Command("shouldNotExist"));

        ArgumentCaptor<String> responseArg = ArgumentCaptor.forClass(String.class);
        verify(responseWriter).print(responseArg.capture());

        final ExceptionSummary summary = new Gson().fromJson(responseArg.getValue(), ExceptionSummary.class);
        assertThat(summary.exception, is(CommandNotFoundException.class.getName()));
    }

    private void initMocks() throws IOException {
        when(response.getWriter()).thenReturn(responseWriter);
        when(request.getParameter("commands")).thenReturn("[]");
        when(request.getPathInfo()).thenReturn("/");
        TestRunInformation.setSolo(solo);
    }

    private void handle() throws Exception {
        handler.handle(null, request, response, 0);
    }

    private void post(Command... commands) throws Exception {
        when(request.getParameter("commands")).thenReturn(jsonCommands(commands));
        handle();
    }

    private String jsonCommands(final Command... commands) {
        return new Gson().toJson(commands);
    }
}
