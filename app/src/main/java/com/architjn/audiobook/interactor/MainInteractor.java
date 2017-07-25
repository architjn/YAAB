package com.architjn.audiobook.interactor;

import android.Manifest;
import android.content.Context;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.BookChapter;
import com.architjn.audiobook.presenter.IMainPresenter;
import com.architjn.audiobook.presenter.MainPresenter;
import com.architjn.audiobook.ui.activity.MainActivity;
import com.architjn.audiobook.utils.BookUtils;
import com.architjn.audiobook.utils.PermissionChecker;
import com.architjn.audiobook.utils.PrefUtils;
import com.architjn.audiobook.utils.Utils;

import java.util.ArrayList;

/**
 * Created by HP on 22-07-2017.
 */

public class MainInteractor {

    private final Context context;
    private final IMainPresenter presenter;
    private final PrefUtils pref;

    public MainInteractor(Context context, IMainPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        this.pref = PrefUtils.getInstance(context);
    }

    public void scanForNewAudioBooks() {
        if (PermissionChecker.requestForPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                R.string.read_permission_request, MainActivity.READ_PERMISSION_REQUEST)) {
            if (pref.getAudioBookFolderPath() == null) {
                if (presenter != null)
                    presenter.startFolderChooser();
            } else {
                ArrayList<AudioBook> books = BookUtils.scanForBooks(context, pref.getAudioBookFolderPath());
                for (int i = 0; i < books.size(); i++) {
                    Utils.log(books.get(i).getAlbumName());
                    books.set(i, BookUtils.loadAlbumDetails(context, books.get(i)));
                    for (BookChapter chapter : books.get(i).getChapters()) {
                        Utils.log("====>" + chapter.getTitle());
                    }
                }
            }
        }
    }
}
