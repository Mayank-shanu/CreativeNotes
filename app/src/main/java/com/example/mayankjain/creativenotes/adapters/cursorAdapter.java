package com.example.mayankjain.creativenotes.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.noteContract;




public class cursorAdapter extends CursorAdapter implements Filterable{
    public cursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cursor_adapter_layout,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String mainData = cursor.getString(cursor.getColumnIndex((new noteContract.noteEntry()).TABLE_COLUMN_ACTUAL_DATA));

        ((TextView)view.findViewById(R.id.cursor_data)).setText(
                !mainData.isEmpty() ? mainData :"(Blank)"
        );

        ((TextView)view.findViewById(R.id.cursor_date)).setText(
                cursor.getString(cursor.getColumnIndex((new noteContract.noteEntry()).TABLE_COLUMN_DATE))
        );
        ((LinearLayout)view.findViewById(R.id.adapter_linearLayout)).setBackgroundColor(Color.parseColor(

                cursor.getString(cursor.getColumnIndex((new noteContract.noteEntry()).TABLE_COLUMN_COLOR))

        ));
    }
}
