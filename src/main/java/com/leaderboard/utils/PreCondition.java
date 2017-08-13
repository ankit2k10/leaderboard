package com.leaderboard.utils;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */
public class PreCondition {
    public static void notNull(Object o, String msg) {
        if(o == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void positive(Integer n, String msg) {
        if(n <= 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void nonNegative(Integer n, String msg) {
        if(n < 0) {
            throw new IllegalArgumentException(msg);
        }
    }


    public static void nonEmpty(String str, String msg) {
        if(str == null || str.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }
}
