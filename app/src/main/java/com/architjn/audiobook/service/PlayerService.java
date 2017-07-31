package com.architjn.audiobook.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.architjn.audiobook.bean.BookChapter;
import com.architjn.audiobook.interactor.PlayerInteractor;
import com.architjn.audiobook.interfaces.IPlayerService;

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
    public void setSource(BookChapter chapter) {
        try {
            gotContent = false;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(chapter.getData());
            gotContent = true;
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (interactor != null) {
                        changeAudioFile(interactor.getNextChapter());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeAudioFile(BookChapter nextChapter) {
        try {
            if (mediaPlayer != null && nextChapter != null) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(nextChapter.getData());
                interactor.askToPlay();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int play() {
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

    @Override
    public void onSeekChange(int progress) {
        if (mediaPlayer != null && gotContent)
            mediaPlayer.seekTo(progress);
    }
}
