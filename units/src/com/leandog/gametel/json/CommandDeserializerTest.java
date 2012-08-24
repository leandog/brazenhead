package com.leandog.gametel.json;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leandog.gametel.driver.commands.Command;

public class CommandDeserializerTest {

    Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(Command.class, new CommandDeserializer()).create();
    }

    @Test
    public void itCanGetAnEmptyObject() {
        Command actualCommand = deserialize("{}");
        assertThat(actualCommand.getName(), is(nullValue()));
        assertThat(actualCommand.getArguments(), is(new Object[0]));
    }

    @Test
    public void itCanGetAMethodName() {
        Command actualCommand = deserialize("{name: \"theMethodName\"}");
        assertThat(actualCommand.getName(), is("theMethodName"));
    }

    @Test
    public void itCanGetNullArguments() {
        Command actualCommand = deserialize("{arguments: null}");
        assertThat(actualCommand.getArguments(), is(new Object[0]));
    }
    
    @Test
    public void itCanHandleIfArgumentsIsNotAnArray() {
        Command actualCommand = deserialize("{arguments: 3}");
        assertThat(actualCommand.getArguments(), is(new Object[0]));
    }

    @Test
    public void itCanGetAnIntegerArgument() {
        Command actualCommand = deserialize("{arguments: [3]}");
        assertThat(actualCommand.getArguments(), is(new Object[] { 3 }));
    }
    
    @Test
    public void itCanGetABooleanArgument() {
        Command actualCommand = deserialize("{arguments: [true]}");
        assertThat(actualCommand.getArguments(), is(new Object[] { true }));
    }
    
    @Test
    public void itCanGetAFloatArgument() {
        Command actualCommand = deserialize("{arguments: [3.0]}");
        assertThat(actualCommand.getArguments(), is(new Object[] { 3.0f }));
    }
    
    @Test
    public void itCanGetADoubleArgument() {
        Double maxDouble = Double.MAX_VALUE;
        Command actualCommand = deserialize("{arguments: [" + maxDouble.toString() + "]}");
        assertThat(actualCommand.getArguments(), is(new Object[] { maxDouble }));
    }
    
    @Test
    public void itCanGetAStringArgument() {
        Command actualCommand = deserialize("{arguments: [\"some string\"]}");
        assertThat(actualCommand.getArguments(), is(new Object[] { "some string" }));
    }

    private Command deserialize(final String json) {
        return gson.fromJson(json, Command.class);
    }

}
