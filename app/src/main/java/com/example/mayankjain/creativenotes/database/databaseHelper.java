package com.example.mayankjain.creativenotes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mayankjain.creativenotes.noteContract.noteEntry;

public class databaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes_database.db";
    private static final int DATABASE_VERSION = 1;

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        noteEntry entry = new noteEntry();
        String createTableCommand = "CREATE TABLE " + entry.TABLE_NAME + " (" +
                entry.TABLE_COLUMN_ACTUAL_DATA + " TEXT, "
                + entry.TABLE_COLUMN_COLOR + " TEXT,"
                + entry.TABLE_COLUMN_DATE + " TEXT, "
                + entry.TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + entry.TABLE_COLUMN_SIZE + " INTEGER, "
                + entry.TABLE_COLUMN_TIME + " INTEGER, "
                + entry.TABLE_COLUMN_PRIORITY + " INTEGER" + " );";
        Log.e("trekking dataBase comma",createTableCommand);
        db.execSQL(createTableCommand);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
