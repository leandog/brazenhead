package com.leandog.brazenhead;

import com.robotium.solo.Solo;

import android.os.Bundle;

public class TestRunInformation {
    
    private static String packageName;
    private static String fullLauncherName;
    private static Solo robotium;
    private static Brazenhead brazenhead;
    
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

    public static Brazenhead getBrazenhead() {
        return brazenhead;
    }

    public static void setBrazenhead(Brazenhead brazenheadInstance) {
        brazenhead = brazenheadInstance;
    }
    
}
