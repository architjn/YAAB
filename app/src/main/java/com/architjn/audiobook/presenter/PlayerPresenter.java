package com.architjn.audiobook.presenter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.interactor.PlayerInteractor;
import com.architjn.audiobook.interfaces.IPlayerPresenter;
import com.architjn.audiobook.interfaces.IPlayerView;
import com.architjn.audiobook.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Archit on 26-07-2017.
 */

public class PlayerPresenter implements IPlayerPresenter {
    private final PlayerInteractor interactor;
    private IPlayerView view;
    private Context context;
    private ImageView art;
    private AudioBook audioBook;

    public View.OnClickListener onPlayBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (interactor!=null)
                interactor.playBook();
        }
    };
    public View.OnClickListener onPrevBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    public View.OnClickListener onNextBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public PlayerPresenter(Context context) {
        this.context = context;
        this.view = (IPlayerView) context;
        this.interactor = PlayerInteractor.getInstance(context);
        this.interactor.setPresenterListener(this);
    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            fetchColors(bitmap);
            art.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    public void setImageView(ImageView art) {
        this.art = art;
        int width = Utils.getScreenWidth();
        int height = width;
        Uri img = Utils.getUriOfMedia(audioBook.getAlbumId());
        art.getLayoutParams().height = height;
        Picasso.with(context)
                .load(img)
                .resize(width, height)
                .centerCrop()
                .into(target);
    }

    private void fetchColors(Bitmap img) {
        Palette.from(img).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                int bodyText = 0, titleText = 0, background = 0,
                        dominantColor = palette.getDominantColor(context.getResources().getColor(R.color.colorAccent));
                if (vibrantSwatch != null && view != null) {
                    background = vibrantSwatch.getRgb();
                    bodyText = vibrantSwatch.getBodyTextColor();
                    titleText = vibrantSwatch.getTitleTextColor();
                } else if (mutedSwatch != null && view != null) {
                    background = mutedSwatch.getRgb();
                    bodyText = mutedSwatch.getBodyTextColor();
                    titleText = mutedSwatch.getTitleTextColor();
                }
                if (bodyText == 0 || view == null)
                    return;
                bodyTextAnimator(bodyText);
                titleTextAnimator(titleText);
                backgroundAnimator(background);
                view.onDominantColorLoad(dominantColor);
            }
        });

    }

    private void backgroundAnimator(int background) {
        int colorFrom = context.getResources().getColor(R.color.background);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, background);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.onBackgroundColorUpdate((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    private void titleTextAnimator(int titleText) {
        int colorFrom = context.getResources().getColor(R.color.colorAccent);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, titleText);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.onTitleTextColorUpdate((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    private void bodyTextAnimator(int bodyText) {
        int colorFrom = context.getResources().getColor(R.color.default_text_color);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, bodyText);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.onBodyTextColorUpdate((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    public void loadAudioBook(String albumId) {
        this.audioBook = interactor.loadBook(albumId);
        if (audioBook == null) {
            if (view != null)
                view.onErrorLoadAudioBook();
        }else if (view!=null)
            view.onInit();
    }

    public AudioBook getAudioBook() {
        return audioBook;
    }
}
