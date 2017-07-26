package com.architjn.audiobook.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.presenter.PlayerPresenter;
import com.architjn.audiobook.ui.IPlayerView;
import com.architjn.audiobook.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Archit on 26-07-2017.
 */

public class PlayerActivity extends AppCompatActivity implements IPlayerView {
    private PlayerPresenter presenter;
    @BindView(R.id.song_name)
    TextView songName;
    @BindView(R.id.artist)
    TextView artist;
    @BindView(R.id.image)
    ImageView art;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.time_left)
    TextView timeLeft;
    @BindView(R.id.time_right)
    TextView timeRight;
    private AudioBook audiobook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            lp.setMargins(0, Utils.getStatusBarHeight(), 0, 0);
        }
        audiobook = (AudioBook) getIntent().getSerializableExtra("audiobook");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        presenter = new PlayerPresenter(this);
        songName.setText(audiobook.getAlbumName());
        artist.setText(audiobook.getArtistName());
        presenter.setImageView(art, audiobook);
    }

    @Override
    public void onBodyTextColorUpdate(int animatedValue) {
        songName.setTextColor(animatedValue);
    }

    @Override
    public void onDominantColorLoad(int dominantColor) {
        timeLeft.setTextColor(dominantColor);
        timeRight.setTextColor(dominantColor);
    }

    @Override
    public void onBackgroundColorUpdate(int animatedValue) {
        back.setBackgroundColor(animatedValue);
    }

    @Override
    public void onTitleTextColorUpdate(int animatedValue) {
        artist.setTextColor(animatedValue);
    }
}
