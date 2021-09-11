package com.church.punithaanthoniyar.utils;

import android.util.Log;

import com.church.punithaanthoniyar.BuildConfig;
import com.church.punithaanthoniyar.base.ApplicationConfigs;

public class Commons {
    public static final int DATE_TIME = 5;
    public static final int DATE = 6;

    public static void print( String message) {
        if (BuildConfig.DEBUG)
            Log.d(ApplicationConfigs.LOG, message);
    }

    public static void printException( String message, Throwable e) {
        Log.e(ApplicationConfigs.LOG, message, e);
    }

    public static void printException(Throwable e) {
        Log.e(ApplicationConfigs.LOG, "~", e);
    }

    public static void printException(String message) {
        Log.e(ApplicationConfigs.LOG, message);
    }

    public static void printInformation(String message) {
        if (BuildConfig.DEBUG)
            Log.i(ApplicationConfigs.LOG, message);
    }
}
