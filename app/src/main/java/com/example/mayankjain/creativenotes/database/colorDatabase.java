package com.example.mayankjain.creativenotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mayankjain.creativenotes.noteContract;


public class colorDatabase extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "colors.db";
    private final static int DATABASE_VERSION = 2;

    public colorDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE "+ noteContract.colorEntry.TABLE_NAME  + " ( " +
                noteContract.colorEntry.TABLE_COLUMN_NAME  + " TEXT, " +
                noteContract.colorEntry.TABLE_COLUMN_VALUE + " TEXT, " +
                noteContract.colorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT "+
                " )";
        db.execSQL(create);

        ContentValues values = new ContentValues();
        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"RED");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#FF0000");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"SKY BLUE");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#87CEEB");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"Dove Beige");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#FEE0C6");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"aquamarine");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#7FFFD4");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"CORAL");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#FF7256");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"cyan");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#00FFFF");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"dark golden");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#FFB90F");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"dark olive green");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#A2CD5A");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,"indianred");
        values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#FF6A6A");
        db.insert(noteContract.colorEntry.TABLE_NAME,null,values);
        values.clear();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
