package com.architjn.audiobook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Archit on 25-07-2017.
 */

public class PrefUtils {

    private static final String AUDIOBOOK_PATH = "audioBookFolder";
    private static PrefUtils instance;
    private final SharedPreferences prefMgmnt;

    private PrefUtils(Context context){
        prefMgmnt = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PrefUtils getInstance(Context context){
        if (instance==null)
            instance = new PrefUtils(context);
        return instance;
    }

    public void setAudioBookFolderPath(String path){
        prefMgmnt.edit().
                putString(AUDIOBOOK_PATH, path)
                .apply();
    }

    public String getAudioBookFolderPath() {
        return prefMgmnt.getString(AUDIOBOOK_PATH, null);
    }
}
