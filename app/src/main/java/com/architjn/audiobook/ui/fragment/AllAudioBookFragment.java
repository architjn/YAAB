package com.architjn.audiobook.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.architjn.audiobook.R;

/**
 * Created by HP on 18-07-2017.
 */

public class AllAudioBookFragment extends Fragment {
    private View mainView;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_audiobook_all, container, false);
        context = mainView.getContext();
        init();
        return mainView;
    }

    private void init() {

    }
}
