package com.mywork.bowlingscore.common;

import android.util.Log;

/**
 * Created by ali_d on 4/11/18.
 */

public class Logger {
    public static final boolean LOG_ENABLED = true;

    public static void i(String tag, String message) {
        if(!LOG_ENABLED) {
            return;
        }

        Log.i(tag, message);
    }
}
