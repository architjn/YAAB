package com.architjn.audiobook.utils;

import android.content.ContentUris;
import android.net.Uri;
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

    public static Uri getUriOfMedia(String albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                Long.parseLong(albumId));
    }

}
