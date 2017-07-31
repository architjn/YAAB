package com.architjn.audiobook.ui.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.architjn.audiobook.R;
import com.architjn.audiobook.presenter.PlayerPresenter;
import com.architjn.audiobook.interfaces.IPlayerView;
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
    @BindView(R.id.play)
    FloatingActionButton play;
    @BindView(R.id.prev)
    FloatingActionButton prev;
    @BindView(R.id.next)
    FloatingActionButton next;
    @BindView(R.id.seekbar)
    SeekBar seekBar;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        presenter = new PlayerPresenter(this);
        presenter.loadAudioBook(getIntent().getStringExtra("id"));
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

    @Override
    public void onErrorLoadAudioBook() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.no_audiobook)
                .setMessage(R.string.no_audiobook_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isFinishing())
                            finish();
                    }
                })
                .create().show();
    }

    @Override
    public void onInit(long duration) {
        songName.setText(presenter.getAudioBook().getAlbumName());
        artist.setText(presenter.getAudioBook().getArtistName());
        presenter.setImageView(art);
        seekBar.setMax((int) duration / 1000);
        timeRight.setText(Utils.formatTime(duration));
        timeLeft.setText(Utils.formatTime(0));
        play.setOnClickListener(presenter.onPlayBtn);
        prev.setOnClickListener(presenter.onPrevBtn);
        next.setOnClickListener(presenter.onNextBtn);
        seekBar.setOnSeekBarChangeListener(presenter.onSeekChange);
    }

    @Override
    public void updateTimer(final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeLeft.setText(Utils.formatTime(Long.parseLong(time)));
                seekBar.setProgress(Integer.parseInt(time) / 1000);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
