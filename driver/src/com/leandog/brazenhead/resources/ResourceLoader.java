package com.leandog.brazenhead.resources;

import java.io.*;
import java.util.*;

import android.content.Context;

public class ResourceLoader {

    private Context context;

    public ResourceLoader(final Context context) {
        this.context = context;
    }

    public List<String> linesFor(final String resourcePath) throws IOException {
        final List<String> theLines = new ArrayList<String>();
        
        final BufferedReader reader = new BufferedReader(readStream(resourcePath));
        
        String line;
        while(null != (line = reader.readLine())) {
            theLines.add(line);
        }
        
        return theLines;
    }

    private InputStreamReader readStream(final String resourcePath) throws IOException {
        return new InputStreamReader(context.getAssets().open(resourcePath));
    }

}
