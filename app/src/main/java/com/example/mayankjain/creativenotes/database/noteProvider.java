package com.example.mayankjain.creativenotes.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mayankjain.creativenotes.noteContract;

import static android.R.attr.id;
import static android.content.ContentUris.withAppendedId;


public class noteProvider extends ContentProvider {

     /*String[] projection = new String[]{
                    entry.TABLE_COLUMN_ID,
                    entry.TABLE_COLUMN_ACTUAL_DATA,
                    entry.TABLE_COLUMN_COLOR,
                    entry.TABLE_COLUMN_DATE,
                    entry.TABLE_COLUMN_PRIORITY,
                    entry.TABLE_COLUMN_SIZE,
                    entry.TABLE_COLUMN_TIME
            };*/

    @Override
    public boolean onCreate() {
        new databaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {



        databaseHelper dbHelper = new databaseHelper(getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        noteContract.noteEntry entry = new noteContract.noteEntry();

        if(entry.uriMatch().match(uri)==entry.NOTE){
        Cursor cursor = database.query(entry.TABLE_NAME,null,selection,selectionArgs,null,null,sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
            return cursor;
        }

        else if(entry.uriMatch().match(uri)==entry.NOTE_ID){

            selection = entry.TABLE_COLUMN_ID +"=?";
            selectionArgs = new String[]{""+ ContentUris.parseId(uri)};
            Cursor cursor = database.query(entry.TABLE_NAME,null,selection,selectionArgs,null,null,sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
            return cursor;
        }

        else
        {
            Cursor cursor = database.query(entry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
            return cursor;
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        if((new noteContract.noteEntry()).uriMatch().match(uri)== (new noteContract.noteEntry()).NOTE) {
            Log.e("trekking insert","it  is invoked");
            SQLiteDatabase database = (new databaseHelper(getContext())).getWritableDatabase();
            noteContract.noteEntry entry = new noteContract.noteEntry();
            long id = database.insert( entry.TABLE_NAME ,null , values);
            getContext().getContentResolver().notifyChange(uri,null);
            return withAppendedId((new noteContract.noteEntry()).contentUri, id);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        noteContract.noteEntry entry = new noteContract.noteEntry();
        SQLiteDatabase database = (new databaseHelper(getContext())).getWritableDatabase();
        if(entry.uriMatch().match(uri) == entry.NOTE){
            getContext().getContentResolver().notifyChange(uri,null);
            return database.delete(entry.TABLE_NAME,null,null);
        }

        else if(entry.uriMatch().match(uri) == entry.NOTE_ID){

            selection = entry.TABLE_COLUMN_ID +"=?";
            selectionArgs = new String[]{""+ ContentUris.parseId(uri)};
            getContext().getContentResolver().notifyChange(uri,null);
            return database.delete(entry.TABLE_NAME,selection,selectionArgs);

        }
        return -1;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = (new databaseHelper(getContext())).getWritableDatabase();
        noteContract.noteEntry entry = new noteContract.noteEntry();

        if(entry.uriMatch().match(uri) == entry.NOTE_ID){
            selection = entry.TABLE_COLUMN_ID +"=?";
            selectionArgs = new String[]{"" + ContentUris.parseId(uri)};
            getContext().getContentResolver().notifyChange(uri,null);
            return database.update(entry.TABLE_NAME,values,selection,selectionArgs);
        }
        return -1;
    }



}
