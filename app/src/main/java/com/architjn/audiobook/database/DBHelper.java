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

    public void addAudioBook(AudioBook audioBook) {
        AudioBookTable.addBook(this.getWritableDatabase(), audioBook);
        ChapterTable.addChapters(this.getWritableDatabase(), audioBook.getAlbumId(),
                audioBook.getChapters());
    }

    public ArrayList<AudioBook> loadAllAudioBooks() {
        return AudioBookTable.getAllBooks(this.getReadableDatabase());
    }
}
