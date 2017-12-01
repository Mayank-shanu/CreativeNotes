package com.example.mayankjain.creativenotes.update_color_dataBase;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.database.colorDatabase;
import com.example.mayankjain.creativenotes.noteContract;

import static java.security.AccessController.getContext;

public class updateColorsActivity extends AppCompatActivity {
    Boolean booleanToChkDel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_colors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Modify Colors");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),enter_to_update_color.class);
                startActivity(intent);
            }
        });

        ListView listView = ((ListView)findViewById(R.id.color_update_listView));
        updateUI();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),enter_to_update_color.class);
                intent.putExtra("_id",id);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                Toast.makeText(getApplicationContext(),"Long Click Not Support",Toast.LENGTH_SHORT).show();
               /* String[] arr = new String[]{"Delete this color","Share this color","Dismiss Dialog"};
                AlertDialog.Builder builder =  new AlertDialog.Builder(getApplicationContext());
                View longClickView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.long_click_dialogbox_layout,null);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arr);
                ListView listView_long_click = (ListView) longClickView.findViewById(R.id.long_click_dialog_listView);
                listView_long_click.setAdapter(adapter);
                builder.setView(longClickView);
                builder.setCancelable(true);
                final AlertDialog dialog = builder.create();
                dialog.show();*/


                //listView_long_click.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //    @Override
                //    public void onItemClick(AdapterView<?> parent, View view, int position, long id_long_click) {
                //         if(position == 0/* Delete*/){
                //            acnDelete(id);
                //            dialog.dismiss();
                //        }
                //        else if(position == 1/*Share*/){
                //            acnShare(id);
                //            dialog.dismiss();
                //        }
                //        else if(position == 2/*Dismiss*/){
                //            dialog.dismiss();
                //        }
                //    }
                //});

                return true;
            }
        });


    }

    private class colorsAdapter extends CursorAdapter{

        public colorsAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.listview_color_adapter_layout,null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

           TextView name_of_color = ((TextView)view.findViewById(R.id.color_update_name_of_color));
            name_of_color.setText(

                    cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_NAME))

            );
            /*name_of_color.setTextColor(

                    Color.parseColor(cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE)))

            );*/

            ((TextView)view.findViewById(R.id.color_update_color_of_color)).setBackgroundColor(

                    Color.parseColor(cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE)))
            );


        }
    }

    void updateUI(){
        colorDatabase db = new colorDatabase(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(noteContract.colorEntry.TABLE_NAME,null,null,null,null,null,noteContract.colorEntry.TABLE_COLUMN_NAME);
        colorsAdapter adapter = new colorsAdapter(getApplicationContext(),cursor);
        ListView listView = ((ListView)findViewById(R.id.color_update_listView));
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }
}
