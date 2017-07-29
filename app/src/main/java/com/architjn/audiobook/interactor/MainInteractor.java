package com.architjn.audiobook.interactor;

import android.Manifest;
import android.content.Context;
import android.os.Handler;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.database.DBHelper;
import com.architjn.audiobook.interfaces.IMainPresenter;
import com.architjn.audiobook.ui.activity.MainActivity;
import com.architjn.audiobook.utils.BookUtils;
import com.architjn.audiobook.utils.PermissionChecker;
import com.architjn.audiobook.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by HP on 22-07-2017.
 */

public class MainInteractor {

    private final Context context;
    private final IMainPresenter presenter;
    private final PrefUtils pref;
    private final DBHelper dbHandler;

    public MainInteractor(Context context, IMainPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        this.pref = PrefUtils.getInstance(context);
        this.dbHandler = DBHelper.getInstance(context);
    }

    public void scanForNewAudioBooks() {
        if (PermissionChecker.requestForPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                R.string.read_permission_request, MainActivity.READ_PERMISSION_REQUEST)) {
            final Handler handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    if (pref.getAudioBookFolderPath() == null) {
                        if (presenter != null)
                            presenter.startFolderChooser();
                    } else {
                        ArrayList<AudioBook> books = BookUtils.scanForBooks(context, pref.getAudioBookFolderPath());
                        for (int i = 0; i < books.size(); i++) {
                            books.set(i, BookUtils.loadAlbumDetails(context, books.get(i)));
                            if (DBHelper.getInstance(context)
                                    .addAudioBook(books.get(i)))
                                presenter.onNewAudioBookLoaded();
                        }
                    }
                }
            };
            handler.post(r);
        }
    }

    public int getPageWithSomeData() {
        if (dbHandler.getOnGoingBooksCount() > 0) {
            return 2;
        }
        if (dbHandler.getNewBooksCount() > 0) {
            return 1;
        }
        return 0;
    }
}
