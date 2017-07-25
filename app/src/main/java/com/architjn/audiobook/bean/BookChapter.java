package com.architjn.audiobook.bean;

/**
 * Created by Archit on 25-07-2017.
 */

public class BookChapter {
    private final String id;
    private final String title;
    private final long duration;

    public BookChapter(String id, String title, long duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getDuration() {
        return duration;
    }
}
