package com.architjn.audiobook.interactor;

import android.os.Environment;

import com.architjn.audiobook.bean.Folder;
import com.architjn.audiobook.interfaces.IFolderChooserPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by HP on 23-07-2017.
 */

public class FolderChooserInteractor {
    private IFolderChooserPresenter presenter;

    public FolderChooserInteractor(IFolderChooserPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadFolders(String path) {
        ArrayList<Folder> folders = new ArrayList<>();
        File mainDir;
        if (path == null) mainDir = Environment.getExternalStorageDirectory();
        else mainDir = new File(path);
        File[] files = mainDir.listFiles();
        Arrays.sort(files);
        for (File inFile : files) {
            if (inFile.isDirectory()) {
                folders.add(new Folder(
                        inFile.getAbsolutePath(),
                        inFile.getName()
                ));
            }
        }
        if (presenter!=null)
            presenter.foldersLoaded(mainDir.getAbsolutePath(), folders);
    }
}
