package com.richdataco.common.util;

/**
 * Created by OlaWang on 2017/5/2.
 */

public class JavaLog {

    public static void i(String format, Object... args) {
        StringBuffer ft = new StringBuffer(format);
        if (!ft.toString().endsWith("\n")) {
            ft.append("\n");
        }
        System.out.printf(ft.toString(), args);
    }

    public static void e(String format, Object... args) {
        StringBuffer ft = new StringBuffer(format);
        if (!ft.toString().endsWith("\n")) {
            ft.append("\n");
        }
        System.err.printf(ft.toString(), args);
    }

}
