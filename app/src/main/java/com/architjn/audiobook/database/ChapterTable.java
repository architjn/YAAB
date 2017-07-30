package com.architjn.audiobook.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.tech.NfcA;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.BookChapter;

import java.util.ArrayList;

/**
 * Created by Archit on 25-07-2017.
 */

public class ChapterTable {

    private static final String ID = "chapter_id";
    private static final String TITLE = "chapter_title";
    private static final String ALBUM_ID = "chapter_album_id";
    private static final String DURATION = "chapter_duration";
    private static final String CURRENT_DURATION = "chapter_curr_duration";
    private static final String PATH = "path";
    private static String TABLE_NAME = "chapter";

    public static void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " TEXT PRIMARY KEY," +
                TITLE + " TEXT," +
                ALBUM_ID + " TEXT," +
                PATH + " TEXT," +
                CURRENT_DURATION + " TEXT DEFAULT 0," +
                DURATION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public static void addChapters(SQLiteDatabase db, String albumId,
                                   ArrayList<BookChapter> chapters) {
        for (BookChapter chapter : chapters) {
            ContentValues values = new ContentValues();
            values.put(ALBUM_ID, albumId);
            values.put(ID, chapter.getId());
            values.put(TITLE, chapter.getTitle());
            values.put(PATH, chapter.getData());
            values.put(DURATION, chapter.getDuration());
            db.insert(TABLE_NAME, null, values);
        }
    }

    public static ArrayList<BookChapter> getChapters(SQLiteDatabase db, String albumId) {
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ALBUM_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{albumId});
        ArrayList<BookChapter> chapters = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                chapters.add(new BookChapter(
                        cursor.getString(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(TITLE)),
                        Long.parseLong(cursor.getString(cursor.getColumnIndex(DURATION))),
                        cursor.getString(cursor.getColumnIndex(PATH)),
                        Long.parseLong(cursor.getString(cursor.getColumnIndex(CURRENT_DURATION)))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return chapters;
    }
}
