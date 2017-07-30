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

import static com.architjn.audiobook.interactor.PlayerInteractor.Status.PAUSED;
import static com.architjn.audiobook.interactor.PlayerInteractor.Status.PLAYING;

/**
 * Created by HP on 30-07-2017.
 */

public class PlayerService extends Service implements IPlayerService {

    private MediaPlayer mediaPlayer;
    private boolean gotContent;
    private PlayerInteractor interactor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        interactor = PlayerInteractor.getInstance(this);
        interactor.setServiceListener(this);
        mediaPlayer = new MediaPlayer();
        gotContent = false;
        return START_STICKY;
    }

    @Override
    public void setSource(AudioBook audiobook) {
        try {
            gotContent = false;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audiobook.getChapters().get(0).getData());
            gotContent = true;
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int play() {
        Utils.log("Playing");
        if (mediaPlayer != null && gotContent) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                interactor.setStatus(PLAYING);
            } else if (mediaPlayer != null) {
                mediaPlayer.pause();
                interactor.setStatus(PAUSED);
            }
            return mediaPlayer.getCurrentPosition();
        }
        return -1;
    }
}
