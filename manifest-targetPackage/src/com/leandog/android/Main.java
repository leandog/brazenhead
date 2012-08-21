package com.leandog.android;

public class Main {

    public static void main(String... args) {
        try {
            new TargetPackageReplacer(args[0]).replace(args[1]);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
