package com.architjn.audiobook.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HP on 23-07-2017.
 */

public class AudioBook implements Serializable {
    private String albumId;
    private String albumKey;
    private String albumName;
    private String artistName;
    private String albumArt;
    private int status;
    private long duration;
    private long currentDuration;
    private ArrayList<BookChapter> chapters;

    public AudioBook() {
        //for serializable
    }

    public AudioBook(String albumId, String albumKey, String albumName, String artistName,
                     String albumArt, int status, long duration, long currentDuration) {
        this.albumId = albumId;
        this.albumKey = albumKey;
        this.albumName = albumName;
        this.artistName = artistName;
        this.albumArt = albumArt;
        this.status = status;
        this.duration = duration;
        this.currentDuration = currentDuration;
    }

    public String getAlbumKey() {
        return albumKey;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public long getDuration() {
        return duration;
    }

    public void setChapters(ArrayList<BookChapter> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<BookChapter> getChapters() {
        return chapters;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setAlbumKey(String albumKey) {
        this.albumKey = albumKey;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(long currentDuration) {
        this.currentDuration = currentDuration;
    }
}
