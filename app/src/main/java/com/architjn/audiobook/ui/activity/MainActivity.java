package com.architjn.audiobook.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.architjn.audiobook.R;
import com.architjn.audiobook.adapter.ViewPagerAdapter;
import com.architjn.audiobook.bean.Folder;
import com.architjn.audiobook.presenter.MainPresenter;
import com.architjn.audiobook.ui.IMainView;
import com.architjn.audiobook.ui.fragment.AllAudioBookFragment;
import com.architjn.audiobook.ui.fragment.FinishedAudioBookFragment;
import com.architjn.audiobook.ui.fragment.NewAudioBookFragment;
import com.architjn.audiobook.ui.fragment.OnGoingAudioBookFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.setupViewPager(viewPager, getSupportFragmentManager());
        presenter.setTabLayout(tabLayout);
        startActivity(new Intent(this, FolderChooserViewActivity.class));
    }
}
