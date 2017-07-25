package com.architjn.audiobook.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;

import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.BookChapter;

import java.util.ArrayList;

/**
 * Created by Archit on 25-07-2017.
 */

public class BookUtils {

    private static Uri MEDIA_PROVIDER = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    public static ArrayList<AudioBook> scanForBooks(Context context, String path) {
        ArrayList<AudioBook> books = new ArrayList<>();
        Utils.log("Checking for books : " + path);
        String[] strArr = new String[]{
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM_KEY,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                "SUM(" + MediaStore.Audio.Media.DURATION + ")"};
        Cursor cursor = context.getContentResolver().query(MEDIA_PROVIDER, strArr,
                MediaStore.Audio.Media.DATA + " LIKE ?) GROUP BY ("
                        + MediaStore.Audio.Media.ALBUM_KEY, new String[]{path + "%"}, MediaStore.Audio.Media.ALBUM);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String albumId = cursor.getString(cursor.getColumnIndex(strArr[0]));
                Cursor cursor1 = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=?",
                        new String[]{String.valueOf(albumId)},
                        null);

                String albumArt = null;
                if (cursor1 != null && cursor1.moveToFirst()) {
                    try {
                        albumArt = cursor1.getString(cursor1.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } finally {
                        cursor1.close();
                    }
                }
                books.add(new AudioBook(
                        albumId,
                        cursor.getString(cursor.getColumnIndex(strArr[1])),
                        cursor.getString(cursor.getColumnIndex(strArr[2])),
                        cursor.getString(cursor.getColumnIndex(strArr[3])),
                        albumArt,
                        cursor.getLong(cursor.getColumnIndex(strArr[4]))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return books;
    }

    public static AudioBook loadAlbumDetails(Context context, AudioBook audioBook) {
        ArrayList<BookChapter> chapters = new ArrayList<>();
        String[] strArr = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ARTIST_KEY};
        Cursor query = context.getContentResolver().query(MEDIA_PROVIDER, strArr,
                MediaStore.Audio.Media.ALBUM_KEY + "= ?", new String[]{audioBook.getAlbumKey()},
                MediaStore.Audio.Media.TRACK + "," + MediaStore.Audio.Media.TITLE + "," + MediaStore.Audio.Media.DATA);
        if (query != null && query.moveToFirst()) {
            do {
                chapters.add(new BookChapter(
                        query.getString(query.getColumnIndex(strArr[0])),
                        query.getString(query.getColumnIndex(strArr[1])),
                        query.getLong(query.getColumnIndex(strArr[2]))
                ));
            } while (query.moveToNext());
            query.close();
        }
        audioBook.setChapters(chapters);
        return audioBook;
    }

}
