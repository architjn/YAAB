package com.architjn.audiobook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.architjn.audiobook.bean.AudioBook;

import java.util.ArrayList;

/**
 * Created by Archit on 25-07-2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yaab-db";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper instance;

    private final Context context;

    public static DBHelper getInstance(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AudioBookTable.onCreate(db);
        ChapterTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addAudioBook(AudioBook audioBook) {
        if (AudioBookTable.addBook(this.getWritableDatabase(), audioBook) != -1) {
            ChapterTable.addChapters(this.getWritableDatabase(), audioBook.getAlbumId(),
                    audioBook.getChapters());
            return true;
        } else return false;
    }

    public ArrayList<AudioBook> loadAllAudioBooks() {
        return AudioBookTable.getAllBooks(this.getReadableDatabase());
    }

    public ArrayList<AudioBook> loadNewAudioBooks() {
        return AudioBookTable.getNewBooks(this.getReadableDatabase());
    }

    public ArrayList<AudioBook> loadOnGoingAudioBooks() {
        return AudioBookTable.getOnGoingBooks(this.getReadableDatabase());
    }

    public void updateBookStatus(String albumId, int status) {
        AudioBookTable.setBookStatus(this.getWritableDatabase(), albumId, status);
    }

    public int getOnGoingBooksCount() {
        return AudioBookTable.getOnGoingDataCount(this.getWritableDatabase());
    }
    public int getNewBooksCount() {
        return AudioBookTable.getNewDataCount(this.getWritableDatabase());
    }
}
