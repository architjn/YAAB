package com.architjn.audiobook.presenter.interfaces;

import com.architjn.audiobook.bean.AudioBook;

import java.util.ArrayList;

/**
 * Created by Archit on 25-07-2017.
 */

public interface IAllAudioBookPresenter {
    void onAudioBooksLoaded(ArrayList<AudioBook> books);
}
