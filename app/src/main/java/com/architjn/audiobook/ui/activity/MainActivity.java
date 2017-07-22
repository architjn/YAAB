package com.architjn.audiobook.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.architjn.audiobook.R;
import com.architjn.audiobook.adapter.ViewPagerAdapter;
import com.architjn.audiobook.presenter.MainPresenter;
import com.architjn.audiobook.ui.IMainView;
import com.architjn.audiobook.ui.fragment.AllAudioBookFragment;
import com.architjn.audiobook.ui.fragment.FinishedAudioBookFragment;
import com.architjn.audiobook.ui.fragment.NewAudioBookFragment;
import com.architjn.audiobook.ui.fragment.OnGoingAudioBookFragment;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements IMainView{

    @BindView(R.id.viewpager) private ViewPager viewPager;
    @BindView(R.id.tabs) private TabLayout tabLayout;
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
        presenter = new MainPresenter(this);
        presenter.setupViewPager(viewPager, getSupportFragmentManager());
        presenter.setTabLayout(tabLayout);
    }
}
