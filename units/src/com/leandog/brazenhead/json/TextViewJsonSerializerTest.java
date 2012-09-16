package com.leandog.brazenhead.json;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.widget.TextView;

import com.google.brazenhead.gson.JsonObject;
import com.leandog.brazenhead.test.BrazenheadTestRunner;

@RunWith(BrazenheadTestRunner.class)
public class TextViewJsonSerializerTest {
    
    @Mock TextView theView;
    
    TextViewJsonSerializer textViewAdapter;
    
    @Before
    public void setUp() {
        textViewAdapter = new TextViewJsonSerializer();
    }
    
    @Test
    public void itStillContainsViewInformation() {
        hasFields("id", "classType", "width", "height");
    }
    
    @Test
    public void itReturnsTheText() {
        when(theView.getText()).thenReturn("Some Text");
        assertThat(string("text"), is("Some Text"));
    }
    
    @Test
    public void itReturnsTheHintText() {
        when(theView.getHint()).thenReturn("Some Hint Text");
        assertThat(string("hint"), is("Some Hint Text"));
    }
    
    @Test
    public void itReturnsTheContentDescription() {
        when(theView.getContentDescription()).thenReturn("Some Content Description");
        assertThat(string("contentDescription"), is("Some Content Description"));
    }

    private String string(final String field) {
        return serialize().get(field).getAsString();
    }

    private JsonObject serialize() {
        return (JsonObject) textViewAdapter.serialize(theView, null, null);
    }

    private void hasFields(final String... fields) {
        final JsonObject serialized = serialize();
        for(final String field : fields) {
            assertThat(String.format("Expected to have the field \"%s\"", field), serialized.has(field), is(true));
        }
    }

}
