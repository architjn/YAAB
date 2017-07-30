package com.architjn.audiobook.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.architjn.audiobook.R;
import com.architjn.audiobook.presenter.MainPresenter;
import com.architjn.audiobook.interfaces.IMainView;
import com.architjn.audiobook.service.PlayerService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView {

    private static final int FOLDER_CHOOSER = 3231;
    public static final int READ_PERMISSION_REQUEST = 7766;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, PlayerService.class));
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.library);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.setupViewPager(viewPager, getSupportFragmentManager());
        presenter.setTabLayout(tabLayout);
    }

    @Override
    public void startFolderChooser() {
        startActivityForResult(new Intent(this, FolderChooserViewActivity.class), FOLDER_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOLDER_CHOOSER && resultCode == RESULT_OK)
            presenter.getInteractor().scanForNewAudioBooks();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_PERMISSION_REQUEST && grantResults[0] == PermissionChecker.PERMISSION_GRANTED)
            presenter.getInteractor().scanForNewAudioBooks();
    }
}
