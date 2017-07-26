package com.architjn.audiobook.ui;

/**
 * Created by Archit on 26-07-2017.
 */

public interface IPlayerView {

    void onBodyTextColorUpdate(int animatedValue);

    void onDominantColorLoad(int dominantColor);

    void onBackgroundColorUpdate(int animatedValue);

    void onTitleTextColorUpdate(int animatedValue);
}
