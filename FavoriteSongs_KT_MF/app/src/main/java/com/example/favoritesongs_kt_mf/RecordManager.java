package com.example.favoritesongs_kt_mf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class RecordManager extends SQLiteOpenHelper {

    private static final String ACTIVITY_TAG = "RecordManager";

    private static final String DB_NAME = "FavoriteSongsDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "favorite_songs";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ARTIST = "artist";
    private static final String RATING = "rating";

    RecordManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateStr = String.format(Locale.US,
                "create table %s(" +
                        "%s integer primary key autoincrement, " +
                        "%s text, " +
                        "%s text, " +
                        "%s integer)",
                TABLE_NAME, ID, NAME, ARTIST, RATING);
        Log.w(ACTIVITY_TAG, String.format(Locale.US,
                "creating database, sqlCreateStr: %s", sqlCreateStr));
        db.execSQL(sqlCreateStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old table if it exists
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    void insert(Song songRec) {
        String sqlInsertStr = String.format(Locale.US,
                "insert into %s values( null, '%s', '%s', '%d' )",
                TABLE_NAME, songRec.getName(), songRec.getArtist(), songRec.getRating());
        Log.w(ACTIVITY_TAG, String.format(Locale.US,
                "about to execute \"%s\"", sqlInsertStr));

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsertStr);
        db.close();
    }

    public void deleteById(int delId) {
        String sqlDeleteStr = String.format(Locale.US,
                "delete from %s where %s = %d",
                TABLE_NAME, ID, delId);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlDeleteStr);
        db.close();
    }

    public Song selectById(int desiredId) {
        String sqlQueryStr = String.format(Locale.US,
                "select * from %s where %s = %d",
                TABLE_NAME, ID, desiredId);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQueryStr, null);
        db.close();

        Song songRec = null;
        if (cursor.moveToFirst()) {
            songRec = new Song(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)));
        }
        cursor.close();
        return songRec;
    }

    public ArrayList<Song> selectAll() {
        String sqlQueryStr = String.format(Locale.US,
                "select * from %s", TABLE_NAME);
        Log.w(ACTIVITY_TAG, String.format(Locale.US,
                "composed query \"%s\"", sqlQueryStr));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQueryStr, null);
        Log.w(ACTIVITY_TAG, "ran the query");

        ArrayList<Song> songRecList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Song songRec = new Song(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)));
            songRecList.add(songRec);
        }
        cursor.close();
        db.close(); /* must be finished with the cursor before closing the db */
        Log.w(ACTIVITY_TAG, "processed results, closed db");

        return songRecList;
    }


}
