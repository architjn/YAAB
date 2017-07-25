package com.architjn.audiobook.interactor;

import android.content.Context;
import android.os.Handler;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.database.DBHelper;
import com.architjn.audiobook.presenter.interfaces.IAllAudioBookPresenter;

import java.util.ArrayList;

/**
 * Created by Archit on 25-07-2017.
 */

public class OnGoingAudioBookInteractor {

    private final DBHelper dbHandler;
    private IAllAudioBookPresenter presenter;
    private ArrayList<AudioBook> books;

    public OnGoingAudioBookInteractor(Context context, IAllAudioBookPresenter presenter) {
        this.presenter = presenter;
        this.dbHandler = DBHelper.getInstance(context);
    }

    public void loadOnGoingwAudioBooks() {
        books = new ArrayList<>();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                books = dbHandler.loadOnGoingAudioBooks();
                if (presenter != null)
                    presenter.onAudioBooksLoaded(books);
            }
        };
        handler.post(r);
    }

    public void markAudioBookFinished(AudioBook item) {
        dbHandler.updateBookStatus(item.getAlbumId(), 2);
    }
}
