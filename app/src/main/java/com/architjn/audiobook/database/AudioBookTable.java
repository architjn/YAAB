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
    private static final String ALBUM_STATUS = "album_status";
    private static final String ARTIST_NAME = "artist_name";
    private static final String COMPLETE_DURATION = "complete_duration";

    public static void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ALBUM_ID + " TEXT PRIMARY KEY," +
                ALBUM_KEY + " TEXT," +
                ALBUM_NAME + " TEXT," +
                ALBUM_ART + " TEXT," +
                ARTIST_NAME + " TEXT," +
                ALBUM_STATUS + " INTEGER DEFAULT 0," +
                COMPLETE_DURATION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public static long addBook(SQLiteDatabase db, AudioBook audioBook) {
        ContentValues values = new ContentValues();
        values.put(ALBUM_ID, audioBook.getAlbumId());
        values.put(ALBUM_KEY, audioBook.getAlbumKey());
        values.put(ALBUM_NAME, audioBook.getAlbumName());
        values.put(ALBUM_ART, audioBook.getAlbumArt());
        values.put(ARTIST_NAME, audioBook.getArtistName());
        values.put(COMPLETE_DURATION, audioBook.getDuration());
        return db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
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
                        cursor.getInt(cursor.getColumnIndex(ALBUM_STATUS)),
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

    public static void setBookStatus(SQLiteDatabase db, String albumId, int status) {
        ContentValues values = new ContentValues();
        values.put(ALBUM_STATUS, status);
        db.update(TABLE_NAME, values, ALBUM_ID + "=?", new String[]{albumId});
    }

    public static ArrayList<AudioBook> getNewBooks(SQLiteDatabase db) {
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ALBUM_STATUS + "=0";
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
                        cursor.getInt(cursor.getColumnIndex(ALBUM_STATUS)),
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

    public static ArrayList<AudioBook> getOnGoingBooks(SQLiteDatabase db) {
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ALBUM_STATUS + "=1";
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
                        cursor.getInt(cursor.getColumnIndex(ALBUM_STATUS)),
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

    public static int getNewDataCount(SQLiteDatabase db) {
        String query = "SELECT  count(*) FROM " + TABLE_NAME + " WHERE " + ALBUM_STATUS + "=0";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public static int getOnGoingDataCount(SQLiteDatabase db) {
        String query = "SELECT  count(*) FROM " + TABLE_NAME + " WHERE " + ALBUM_STATUS + "=1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

}
