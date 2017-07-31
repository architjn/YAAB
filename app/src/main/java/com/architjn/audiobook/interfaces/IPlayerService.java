package com.architjn.audiobook.interfaces;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.BookChapter;

/**
 * Created by HP on 30-07-2017.
 */

public interface IPlayerService {
    void setSource(BookChapter audiobook);

    int play();

    void onSeekChange(int progress);
}
