package com.architjn.audiobook.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.architjn.audiobook.R;
import com.architjn.audiobook.adapter.ViewPagerAdapter;
import com.architjn.audiobook.interactor.MainInteractor;
import com.architjn.audiobook.ui.IMainView;
import com.architjn.audiobook.ui.fragment.AllAudioBookFragment;
import com.architjn.audiobook.ui.fragment.FinishedAudioBookFragment;
import com.architjn.audiobook.ui.fragment.NewAudioBookFragment;
import com.architjn.audiobook.ui.fragment.OnGoingAudioBookFragment;

/**
 * Created by HP on 22-07-2017.
 */

public class MainPresenter {
    private final Context context;
    private MainInteractor interactor;
    private IMainView view;
    private ViewPager viewPager;

    public MainPresenter(Context context) {
        this.view = (IMainView) context;
        this.context = context;
        this.interactor = new MainInteractor();
    }

    public void setupViewPager(ViewPager viewPager, FragmentManager supportFragmentManager) {
        this.viewPager = viewPager;
        ViewPagerAdapter adapter = new ViewPagerAdapter(context, supportFragmentManager);
        adapter.addFragment(new AllAudioBookFragment(), R.string.all);
        adapter.addFragment(new NewAudioBookFragment(), R.string.new_txt);
        adapter.addFragment(new OnGoingAudioBookFragment(), R.string.ongoing);
        adapter.addFragment(new FinishedAudioBookFragment(), R.string.finished);
        viewPager.setAdapter(adapter);
    }

    public void setTabLayout(TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
    }
}
