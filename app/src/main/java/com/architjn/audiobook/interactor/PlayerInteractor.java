package com.architjn.audiobook.interactor;

import android.content.Context;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.database.DBHelper;
import com.architjn.audiobook.interfaces.IPlayerPresenter;
import com.architjn.audiobook.interfaces.IPlayerService;

/**
 * Created by HP on 30-07-2017.
 */

public class PlayerInteractor {

    private static PlayerInteractor instance;

    private final Context context;
    private final DBHelper db;
    private IPlayerPresenter presenter;
    private IPlayerService service;
    private Status status;

    public static PlayerInteractor getInstance(Context context) {
        if (instance == null)
            instance = new PlayerInteractor(context);
        return instance;
    }

    private PlayerInteractor(Context context) {
        this.context = context;
        this.db = DBHelper.getInstance(context);
    }

    public void setPresenterListener(IPlayerPresenter presenter) {
        this.presenter = presenter;
    }

    public void setServiceListener(IPlayerService service) {
        this.service = service;
    }

    public AudioBook loadBook(String albumId) {
        AudioBook audiobook = db.getAudioBookViaId(albumId);
        if (service != null && audiobook != null)
            service.setSource(audiobook);
        return audiobook;
    }

    public int playBook() {
        if (service != null)
            return service.play();
        else return -1;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        PLAYING, STOPPED, PAUSED
    }
}
