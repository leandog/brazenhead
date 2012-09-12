package com.leandog.brazenhead;

import java.util.*;
import java.util.regex.*;

import android.app.Instrumentation;

import com.leandog.brazenhead.resources.ResourceLoader;

public class Brazenhead {

    private final Instrumentation instrumentation;
    private final ResourceLoader resourceLoader;

    Map<String, Integer> resourceIds = new HashMap<String, Integer>();
    private Pattern resourceIdRegex = Pattern.compile("\\s+resource 0x(\\p{XDigit}+) .*:id/(\\w+).*");

    public Brazenhead(final Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
        this.resourceLoader = new ResourceLoader(instrumentation.getContext());
    }

    public Brazenhead(final Instrumentation instrumentation, final ResourceLoader resourceLoader) {
        this.instrumentation = instrumentation;
        this.resourceLoader = resourceLoader;
    }

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public int idFromName(final String namedId) {
        if (hasNotLoadedTheIds()) {
            loadResourceIds();
        }
        
        if( !resourceIds.containsKey(namedId) ) {
            throw new IllegalArgumentException(String.format("The id \"%s\" was not found within the resources.", namedId));
        }
        
        return resourceIds.get(namedId);
    }

    private boolean hasNotLoadedTheIds() {
        return resourceIds.isEmpty();
    }

    private void loadResourceIds() {
        List<String> resourceLines = resourceLoader.linesFor("resources.txt");
        for (final String resourceLine : resourceLines) {
            final Matcher matcher = match(resourceLine);
            if (matcher.matches()) {
                resourceIds.put(matcher.group(2), Integer.parseInt(matcher.group(1), 16));
            }
        }
    }

    private Matcher match(final String resourceLine) {
        return resourceIdRegex.matcher(resourceLine);
    }

}
