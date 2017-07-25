package com.architjn.audiobook.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.BookChapter;

import java.util.ArrayList;

/**
 * Created by Archit on 25-07-2017.
 */

public class AudioBookTable {

    private static String TABLE_NAME = "audiobook";

    private static final String ALBUM_ID = "album_id";
    private static final String ALBUM_KEY = "album_key";
    private static final String ALBUM_NAME = "album_name";
    private static final String ALBUM_ART = "album_art";
    private static final String ARTIST_NAME = "artist_name";
    private static final String COMPLETE_DURATION = "complete_duration";

    public static void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ALBUM_ID + " TEXT PRIMARY KEY," +
                ALBUM_KEY + " TEXT," +
                ALBUM_NAME + " TEXT," +
                ALBUM_ART + " TEXT," +
                ARTIST_NAME + " TEXT," +
                COMPLETE_DURATION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public static void addBook(SQLiteDatabase db, AudioBook audioBook) {
        ContentValues values = new ContentValues();
        values.put(ALBUM_ID, audioBook.getAlbumId());
        values.put(ALBUM_KEY, audioBook.getAlbumKey());
        values.put(ALBUM_NAME, audioBook.getAlbumName());
        values.put(ALBUM_ART, audioBook.getAlbumArt());
        values.put(ARTIST_NAME, audioBook.getArtistName());
        values.put(COMPLETE_DURATION, audioBook.getDuration());
        db.insert(TABLE_NAME, null, values);
    }

    public static ArrayList<AudioBook> getAllBooks(SQLiteDatabase db) {
        String query = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<AudioBook> books = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                AudioBook audiobook = new AudioBook(
                        cursor.getString(cursor.getColumnIndex(ALBUM_ID)),
                        cursor.getString(cursor.getColumnIndex(ALBUM_KEY)),
                        cursor.getString(cursor.getColumnIndex(ALBUM_NAME)),
                        cursor.getString(cursor.getColumnIndex(ARTIST_NAME)),
                        cursor.getString(cursor.getColumnIndex(ALBUM_ART)),
                        Long.parseLong(cursor.getString(cursor.getColumnIndex(COMPLETE_DURATION))));
                ArrayList<BookChapter> chapters =
                        ChapterTable.getChapters(db, audiobook.getAlbumId());
                audiobook.setChapters(chapters);
                books.add(audiobook);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return books;
    }
}
