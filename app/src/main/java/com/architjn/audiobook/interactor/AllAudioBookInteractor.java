package com.architjn.audiobook.interactor;

import android.content.Context;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.database.DBHelper;
import com.architjn.audiobook.presenter.interfaces.IAllAudioBookPresenter;

import java.util.ArrayList;

/**
 * Created by Archit on 25-07-2017.
 */

public class AllAudioBookInteractor {

    private final DBHelper dbHandler;
    private IAllAudioBookPresenter presenter;

    public AllAudioBookInteractor(Context context, IAllAudioBookPresenter presenter) {
        this.presenter = presenter;
        this.dbHandler = DBHelper.getInstance(context);
    }

    public void loadAllAudioBooks() {
        ArrayList<AudioBook> books = dbHandler.loadAllAudioBooks();
        if (presenter!=null)
            presenter.onAudioBooksLoaded(books);
    }
}
