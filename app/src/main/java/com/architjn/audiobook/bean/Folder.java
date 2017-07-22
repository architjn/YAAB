package com.architjn.audiobook.bean;

/**
 * Created by HP on 23-07-2017.
 */

public class Folder {
    private final String absolutePath;
    private final String name;

    public Folder(String absolutePath, String name) {
        this.absolutePath = absolutePath;
        this.name = name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getName() {
        return name;
    }
}
