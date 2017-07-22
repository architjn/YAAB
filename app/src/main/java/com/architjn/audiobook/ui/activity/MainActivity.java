package com.architjn.audiobook.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.architjn.audiobook.R;
import com.architjn.audiobook.adapter.ViewPagerAdapter;
import com.architjn.audiobook.ui.fragment.AllAudioBookFragment;
import com.architjn.audiobook.ui.fragment.FinishedAudioBookFragment;
import com.architjn.audiobook.ui.fragment.NewAudioBookFragment;
import com.architjn.audiobook.ui.fragment.OnGoingAudioBookFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new AllAudioBookFragment(), R.string.all);
        adapter.addFragment(new NewAudioBookFragment(), R.string.new_txt);
        adapter.addFragment(new OnGoingAudioBookFragment(), R.string.ongoing);
        adapter.addFragment(new FinishedAudioBookFragment(), R.string.finished);
        viewPager.setAdapter(adapter);
    }
}
