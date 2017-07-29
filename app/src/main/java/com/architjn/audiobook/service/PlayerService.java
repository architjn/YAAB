package com.architjn.audiobook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.architjn.audiobook.interactor.PlayerInteractor;
import com.architjn.audiobook.interfaces.IPlayerService;

/**
 * Created by HP on 30-07-2017.
 */

public class PlayerService extends Service implements IPlayerService {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PlayerInteractor.getInstance(this).setServiceListener(this);
        return START_STICKY;
    }
}
