package com.architjn.audiobook.utils;

import android.content.ContentUris;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import com.architjn.audiobook.BuildConfig;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.round;

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

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String formatTime(long millis) {
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
