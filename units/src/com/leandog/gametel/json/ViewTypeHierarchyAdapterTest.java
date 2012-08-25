package com.leandog.gametel.json;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.view.View;

import com.google.gson.*;
import com.leandog.gametel.driver.test.GametelTestRunner;

@RunWith(GametelTestRunner.class)
public class ViewTypeHierarchyAdapterTest {
    
    @Mock View theView;
    ViewTypeHeirarchyAdapter viewAdapter;
    
    @Before
    public void setUp() {
        viewAdapter = new ViewTypeHeirarchyAdapter();
    }
    
    @Test
    public void itReturnsTheId() {
        when(theView.getId()).thenReturn(123);
        assertThat(integer("id"), is(123));
    }
    
    @Test
    public void itReturnsTheClass() {
        assertThat(string("classType"), is(theView.getClass().getName()));
    }
    
    @Test
    public void itReturnsTheWidth() {
        when(theView.getWidth()).thenReturn(123);
        assertThat(integer("width"), is(123));
    }
    
    @Test
    public void itReturnsTheHeight() {
        when(theView.getHeight()).thenReturn(123);
        assertThat(integer("height"), is(123));
    }
    
    @Test
    public void itReturnsTheScreenLocation() {
        assertThat(integerArray("screenLocation"), is(new int[] {0, 0}));
        verify(theView).getLocationOnScreen((int[])any());
    }
    
    @Test
    public void itReturnsTheLocationInTheWindow() {
        assertThat(integerArray("windowLocation"), is(new int[] {0, 0}));
        verify(theView).getLocationInWindow((int[])any());
    }
    
    @Test
    public void itReturnsTheLeft() {
        when(theView.getLeft()).thenReturn(123);
        assertThat(integer("left"), is(123));
    }
    
    @Test
    public void itReturnsTheTop() {
        when(theView.getTop()).thenReturn(123);
        assertThat(integer("top"), is(123));
    }
    
    @Test
    public void itReturnsTheRight() {
        when(theView.getRight()).thenReturn(123);
        assertThat(integer("right"), is(123));
    }
    
    @Test
    public void itReturnsTheBottom() {
        when(theView.getBottom()).thenReturn(123);
        assertThat(integer("bottom"), is(123));
    }
    
    private int integer(final String field) {
        return serialize().get(field).getAsInt();
    }

    private String string(final String field) {
        return serialize().get(field).getAsString();
    }
    
    private int[] integerArray(final String field) {
        final JsonArray jsonArray = serialize().get(field).getAsJsonArray();
        int[] integers = new int[jsonArray.size()];
        for(int index = 0; index < integers.length; index++) {
            integers[index] = jsonArray.get(index).getAsInt();
        }
        return integers;
    }

    private JsonObject serialize() {
        JsonObject json = (JsonObject)viewAdapter.serialize(theView, null, null);
        return json;
    }

}
