package com.architjn.audiobook.presenter;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.architjn.audiobook.adapter.AllAudioBookAdapter;
import com.architjn.audiobook.adapter.IAdapterItemClick;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.interactor.AllAudioBookInteractor;
import com.architjn.audiobook.presenter.interfaces.IAllAudioBookPresenter;
import com.architjn.audiobook.ui.IAudioBookView;
import com.architjn.audiobook.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by HP on 23-07-2017.
 */

public class AllAudioBookPresenter implements IAdapterItemClick<AudioBook>, IAllAudioBookPresenter {
    private final PrefUtils pref;
    private final AllAudioBookInteractor interactor;
    private IAudioBookView view;
    private Context context;
    private AllAudioBookAdapter adapter;

    public AllAudioBookPresenter(Context context, IAudioBookView view) {
        this.view = view;
        this.context = context;
        this.pref = PrefUtils.getInstance(context);
        this.interactor = new AllAudioBookInteractor(context, this);
    }

    public void setRecycler(RecyclerView rv) {
        LinearLayoutManager lm = new LinearLayoutManager(context);
        rv.setLayoutManager(lm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                lm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        ArrayList<AudioBook> items = new ArrayList<>();
        adapter = new AllAudioBookAdapter(context, items, this);
        rv.setAdapter(adapter);
        interactor.loadAllAudioBooks();
    }

    @Override
    public void onItemSelected(int position, AudioBook item) {

    }

    @Override
    public void onAudioBooksLoaded(ArrayList<AudioBook> books) {
        if (adapter != null)
            adapter.updateList(books);
    }
}
