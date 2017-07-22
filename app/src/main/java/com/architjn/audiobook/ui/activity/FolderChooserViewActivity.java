package com.architjn.audiobook.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.architjn.audiobook.R;
import com.architjn.audiobook.presenter.FolderChooserPresenter;
import com.architjn.audiobook.ui.IFolderChooserView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HP on 23-07-2017.
 */

public class FolderChooserViewActivity extends AppCompatActivity implements IFolderChooserView {
    private FolderChooserPresenter presenter;
    @BindView(R.id.rv)
    RecyclerView listView;
    @BindView(R.id.curr_dir)
    TextView currDir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_chooser);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.select_audiobook_folder);
        presenter = new FolderChooserPresenter(this);
        presenter.initializeList(listView);
    }

    @Override
    public void onPathChange(String path) {
        currDir.setText(path);
    }

    @Override
    public void closePage() {
        super.onBackPressed();
    }
}
