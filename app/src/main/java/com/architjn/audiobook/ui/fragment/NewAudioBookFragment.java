package com.architjn.audiobook.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.presenter.NewAudioBookPresenter;
import com.architjn.audiobook.interfaces.IAudioBookView;
import com.architjn.audiobook.ui.activity.PlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HP on 18-07-2017.
 */

public class NewAudioBookFragment extends Fragment implements IAudioBookView {
    private View mainView;
    private Context context;
    private boolean viewDestroyed = true;
    private NewAudioBookPresenter presenter;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_audiobook_new, container, false);
        context = mainView.getContext();
        init();
        return mainView;
    }

    private void init() {
        ButterKnife.bind(this, mainView);
        presenter = new NewAudioBookPresenter(context, this);
        presenter.setRecycler(rv);
        viewDestroyed = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewDestroyed = true;
    }

    public void updateBookList() {
        if (!viewDestroyed)
            presenter.updateBookList();
    }

}
