package com.architjn.audiobook.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.interactor.PlayerInteractor;
import com.architjn.audiobook.interfaces.IPlayerService;
import com.architjn.audiobook.utils.Utils;

import java.io.IOException;

/**
 * Created by HP on 30-07-2017.
 */

public class PlayerService extends Service implements IPlayerService {

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PlayerInteractor.getInstance(this).setServiceListener(this);
        mediaPlayer = new MediaPlayer();
        return START_STICKY;
    }

    @Override
    public void setSource(AudioBook audiobook) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audiobook.getChapters().get(0).getData());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        Utils.log("playing");
        if (mediaPlayer != null)
            mediaPlayer.start();
    }
}
