package com.architjn.audiobook.bean;

import java.io.Serializable;

/**
 * Created by Archit on 25-07-2017.
 */

public class BookChapter implements Serializable{
    private String id;
    private String title;
    private long duration;

    public BookChapter() {
        //for serializable
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
