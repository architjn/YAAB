package com.architjn.audiobook.interfaces;

import com.architjn.audiobook.bean.AudioBook;

/**
 * Created by HP on 30-07-2017.
 */

public interface IPlayerService {
    void setSource(AudioBook audiobook);

    void play();
}
