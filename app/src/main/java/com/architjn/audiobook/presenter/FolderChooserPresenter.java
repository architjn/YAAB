package com.architjn.audiobook.presenter;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.architjn.audiobook.adapter.FolderChooserAdapter;
import com.architjn.audiobook.adapter.IAdapterItemClick;
import com.architjn.audiobook.bean.Folder;
import com.architjn.audiobook.interactor.FolderChooserInteractor;
import com.architjn.audiobook.ui.IFolderChooserView;
import com.architjn.audiobook.utils.Utils;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by HP on 23-07-2017.
 */

public class FolderChooserPresenter implements IFolderChooserPresenter, FolderChooserAdapter.IAdapterItemClick<Folder> {
    private FolderChooserInteractor interactor;
    private IFolderChooserView view;
    private Context context;
    private FolderChooserAdapter adapter;
    private Stack<String> paths;

    public FolderChooserPresenter(Context context) {
        this.view = (IFolderChooserView) context;
        this.context = context;
        this.interactor = new FolderChooserInteractor(this);
        this.paths = new Stack<>();
    }

    public void initializeList(RecyclerView rv) {
        LinearLayoutManager lm = new LinearLayoutManager(context);
        rv.setLayoutManager(lm);
        adapter = new FolderChooserAdapter(context, new ArrayList<Folder>(), this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                lm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);
        interactor.loadFolders(null);
    }

    @Override
    public void foldersLoaded(String currFolder, ArrayList<Folder> folders) {
        adapter.updateList(folders);
        if (view != null)
            view.onPathChange(currFolder);
        paths.push(currFolder);
    }

    @Override
    public void onItemSelected(int position, Folder item) {
        interactor.loadFolders(item.getAbsolutePath());
    }

    @Override
    public void onGoBack() {
        Utils.log("going back"+paths.size());
        if (paths.size() > 1) {
            paths.pop();
            interactor.loadFolders(paths.pop());
        } else
            view.closePage();
    }
}
