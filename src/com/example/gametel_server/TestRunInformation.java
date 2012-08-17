package com.example.gametel_server;

import android.os.Bundle;

public class TestRunInformation {
    
    private static String packageName;
    private static String fullLauncherName;
    
    public static void initialize(final Bundle arguments) {
        if( null == arguments ) return;
        packageName = arguments.getString("packageName");
        fullLauncherName = arguments.getString("fullLauncherName");
    }
    
    public static String getPackageName() {
        return packageName;
    }
    
    public static String getFullLauncherName() {
        return fullLauncherName;
    }
    
}
