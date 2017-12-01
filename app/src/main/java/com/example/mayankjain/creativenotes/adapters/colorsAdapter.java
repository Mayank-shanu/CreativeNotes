package com.example.mayankjain.creativenotes.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.noteContract;

/**
 * Created by mayank jain on 17-Jul-17.
 */

public class colorsAdapter extends CursorAdapter {
    public colorsAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.colors_adapter_layout,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ((TextView)view.findViewById(R.id.color_adapter_textView)).setBackgroundColor(

                Color.parseColor(cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE)))

        );

    }
}
