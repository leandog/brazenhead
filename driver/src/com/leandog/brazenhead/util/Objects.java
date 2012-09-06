package com.leandog.brazenhead.util;

import java.util.Arrays;

public class Objects {

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }

}