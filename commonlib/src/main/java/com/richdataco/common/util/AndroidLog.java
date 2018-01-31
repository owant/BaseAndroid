package com.richdataco.common.util;

import android.util.Log;

/**
 * Created by OlaWang on 2017/5/2.
 */

public class AndroidLog {

    private static boolean SHOW_LOG = true;

    /**
     * you should init in the application
     *
     * @param configShowLog
     */
    public static void init(boolean configShowLog) {
        SHOW_LOG = configShowLog;
    }

    public static void i(String tag, String msg) {
        print(Log.INFO, tag, msg);
    }

    public static void e(String tag, String msg) {
        print(Log.INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        print(Log.WARN, tag, msg);
    }

    private static void print(int index, String tag, Object str) {

        if (!SHOW_LOG) {
            return;
        }

        if (str == null) {
            str = "NULL";
        }

        switch (index) {
            case Log.INFO:
                Log.i(tag, str.toString());
                break;
            case Log.ERROR:
                Log.e(tag, str.toString());
                break;
            case Log.WARN:
                Log.w(tag, str.toString());
                break;
            default:
                break;
        }
    }

}
