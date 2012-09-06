package com.leandog.brazenhead;

import android.os.Bundle;

import com.jayway.android.robotium.solo.Solo;

public class TestRunInformation {
    
    private static String packageName;
    private static String fullLauncherName;
    private static Solo robotium;
    
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
    
    public static void setSolo(final Solo solo) {
        robotium = solo;
    }
    
    public static Solo getSolo() {
        return robotium;
    }
    
}
