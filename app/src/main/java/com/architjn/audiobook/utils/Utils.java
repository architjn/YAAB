package com.architjn.audiobook.utils;

import android.util.Log;

import com.architjn.audiobook.BuildConfig;

/**
 * Created by HP on 23-07-2017.
 */

public class Utils {

    public static void log(String msg) {
        if (BuildConfig.DEBUG)
            Log.e("YAAB-TAG", msg);
    }

}
