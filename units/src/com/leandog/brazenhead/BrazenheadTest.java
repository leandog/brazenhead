package com.leandog.brazenhead;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.*;
import org.mockito.*;

import android.app.Instrumentation;

import com.leandog.brazenhead.resources.ResourceLoader;

public class BrazenheadTest {

    @Mock Instrumentation instrumentation;
    @Mock ResourceLoader resourceLoader;

    private Brazenhead brazenhead;
    private List<String> theLines = new ArrayList<String>();

    @Before
    public void setUp() {
        initMocks();
        brazenhead = new Brazenhead(instrumentation, resourceLoader);
    }

    @Test
    public void itLoadsTheInformationFromTheResources() throws Exception {
        expectToHave("some_id");
        brazenhead.idFromName("some_id");
        verify(resourceLoader).linesFor("resources.txt");
    }

    @Test
    public void itOnlyLoadsTheResourceInformationOnce() {
        expectToHave("some_id");
        brazenhead.idFromName("some_id");
        brazenhead.idFromName("some_id");
        verify(resourceLoader, times(1)).linesFor("resources.txt");
    }

    @Test
    public void itCanCaptureTheResourceNames() throws Exception {
        theLines.add("      spec resource 0x7f0901b9 com.example.android.apis:id/hidden_group: flags=0x00000000");
        theLines.add("      spec resource 0x7f0901ba com.example.android.apis:id/hidden_by_group: flags=0x00000000");
        theLines.add("      config (default):");
        theLines.add("        resource 0x7f090000 com.example.android.apis:id/none: <bag>");
        theLines.add("        resource 0x7f090001 com.example.android.apis:id/thumbnail: <bag>");
        theLines.add("        resource 0x7f090002 com.example.android.apis:id/drop: <bag>");
        theLines.add("        resource 0x7f090003 com.example.android.apis:id/snack: t=0x03 d=0x00000392 (s=0x0008 r=0x00)");
        theLines.add("        resource 0x7f090004 com.example.android.apis:id/button: t=0x12 d=0x00000000 (s=0x0008 r=0x00)");
        theLines.add("        resource 0x7f090005 com.example.android.apis:id/toggle_home_as_up: t=0x12 d=0x00000000 (s=0x0008 r=0x00)");
        theLines.add("        resource 0x7f090006 com.example.android.apis:id/toggle_show_home: t=0x12 d=0x00000000 (s=0x0008 r=0x00)");

        brazenhead.idFromName("none");

        assertThat(brazenhead.resourceIds.keySet(), hasItems("none", "thumbnail", "drop", "snack", "button", "toggle_home_as_up", "toggle_show_home"));
    }

    @Test
    public void itCanCaptureTheValues() throws Exception {
        theLines.add("      spec resource 0x7f0901b9 com.example.android.apis:id/hidden_group: flags=0x00000000");
        theLines.add("      spec resource 0x7f0901ba com.example.android.apis:id/hidden_by_group: flags=0x00000000");
        theLines.add("      config (default):");
        theLines.add("        resource 0x7f090000 com.example.android.apis:id/none: <bag>");
        theLines.add("        resource 0x7f090001 com.example.android.apis:id/thumbnail: <bag>");
        theLines.add("        resource 0x7f090002 com.example.android.apis:id/drop: <bag>");
        theLines.add("        resource 0x7f090003 com.example.android.apis:id/snack: t=0x03 d=0x00000392 (s=0x0008 r=0x00)");
        theLines.add("        resource 0x7f090004 com.example.android.apis:id/button: t=0x12 d=0x00000000 (s=0x0008 r=0x00)");
        theLines.add("        resource 0x7f090005 com.example.android.apis:id/toggle_home_as_up: t=0x12 d=0x00000000 (s=0x0008 r=0x00)");
        theLines.add("        resource 0x7f090006 com.example.android.apis:id/toggle_show_home: t=0x12 d=0x00000000 (s=0x0008 r=0x00)");

        brazenhead.idFromName("none");

        assertThat(brazenhead.resourceIds.values(), hasItems(hex("7f090000"), hex("7f090001"), hex("7f090002"), hex("7f090003")));
        assertThat(brazenhead.resourceIds.values().size(), is(7));
    }

    @Test
    public void itCanGiveMeTheValueOfAnId() {
        theLines.add("        resource 0x7f090006 com.example.android.apis:id/toggle_show_home: t=0x12 d=0x00000000 (s=0x0008 r=0x00)");
        assertThat(brazenhead.idFromName("toggle_show_home"), is(hex("7f090006")));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void itThrowsIfTheIdIsNotFound() {
        brazenhead.idFromName("not_found");
    }

    private Integer hex(final String hexString) {
        return Integer.parseInt(hexString, 16);
    }

    private void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(resourceLoader.linesFor("resources.txt")).thenReturn(theLines);
    }

    private void expectToHave(String theId) {
        theLines.add(String.format("        resource 0x7f090006 com.example.android.apis:id/%s: t=0x12 d=0x00000000 (s=0x0008 r=0x00)", theId));
    }

}
