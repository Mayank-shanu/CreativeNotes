package com.example.mayankjain.creativenotes.display_update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.adapters.colorsAdapter;
import com.example.mayankjain.creativenotes.database.colorDatabase;
import com.example.mayankjain.creativenotes.noteContract;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.format;
import static android.R.attr.id;
import static java.security.AccessController.getContext;
import static junit.runner.Version.id;


public class to_get_diisplay_data extends AppCompatActivity {
    Uri uri;
    String color = "#64ffda";
    int priority;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_get_diisplay_data);
        Intent intent= getIntent();
        priority = intent.getIntExtra("_priority",0);
        uri = intent.getData();

        if(uri != null){
            setValues();
        }
        else ((RelativeLayout)findViewById(R.id.get_main_data_relativeLayout)).setBackgroundColor(Color.parseColor(color));

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(noteContract.userThemePreferences.sharePrefName, Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && preferences != null) {

            Log.e("trekking pref",
                    "status "+
                            preferences.getString(noteContract.userThemePreferences.STATUS_BAR_COLOR,"#0000ff")
                            +" nav "+
                            preferences.getString(noteContract.userThemePreferences.NAVIGATION_BAR_COLOR,"#000000")

            );

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if(preferences.contains(noteContract.userThemePreferences.STATUS_BAR_COLOR))
                window.setStatusBarColor(Color.parseColor(preferences.getString(noteContract.userThemePreferences.STATUS_BAR_COLOR,"#0000ff")));

            if(preferences.contains(noteContract.userThemePreferences.NAVIGATION_BAR_COLOR))
                window.setNavigationBarColor(Color.parseColor(preferences.getString(noteContract.userThemePreferences.NAVIGATION_BAR_COLOR,"#000000")));

            ActionBar bar = getSupportActionBar();
            if(preferences.contains(noteContract.userThemePreferences.ACTION_BAR))
                bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("" +
                        preferences.getString(noteContract.userThemePreferences.ACTION_BAR,"#0000ff") +
                        "")));

            if(preferences.contains(noteContract.userThemePreferences.ACTION_BAR_TEXT))
                bar.setTitle(Html.fromHtml("<font color='" +
                        preferences.getString(noteContract.userThemePreferences.ACTION_BAR_TEXT,"#ffffff") +
                        "'>" + getTitle()+ "</font>"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.to_get_disp_data,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_save){

            EditText text = (EditText)findViewById(R.id.get_mainData_editText);
            if(text.getText().toString().isEmpty())
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("! continue");
                builder.setMessage("Your note is empty sure to save");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(uri == null) insertIntoDatabase();
                        else upDateExistingInfo();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            else{
                if(uri == null) insertIntoDatabase();
                else upDateExistingInfo();
            }

        }

        else if(item.getItemId() == R.id.action_color){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.color_dialog_layout,null);
            GridView gridView = (GridView)view.findViewById(R.id.colors_grid_view);

            colorDatabase data = new colorDatabase(getApplicationContext());
            final SQLiteDatabase database = data.getReadableDatabase();
            Cursor cursor = database.query(noteContract.colorEntry.TABLE_NAME,null,null,null,null,null,noteContract.colorEntry.TABLE_COLUMN_NAME);
            colorsAdapter adapter = new colorsAdapter(getApplicationContext(),cursor);

            gridView.setAdapter(adapter);

            builder.setView(view);
            final AlertDialog dialog = builder.create();
            dialog.show();


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String[] selnArg = new String[]{"" + id};
                    Cursor c = database.query(noteContract.colorEntry.TABLE_NAME,null,
                            noteContract.colorEntry._ID +"=?",
                            selnArg,
                            null,
                            null,
                            null
                    );
                    c.moveToFirst();
                    color = c.getString(c.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE));
                    ((RelativeLayout)findViewById(R.id.get_main_data_relativeLayout)).setBackgroundColor(Color.parseColor(color));
                    c.close();
                    dialog.dismiss();
                }
            });


        }

        else if(item.getItemId() == R.id.action_priority){


            String[] arr = new String[]{"Normal","Favourite","Urgent"};
            AlertDialog.Builder builder =  new AlertDialog.Builder(this);
            builder.setItems(arr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    priority = which;
                }
            });
            builder.setTitle("Priority");
            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }

    void insertIntoDatabase(){
        EditText text = (EditText)findViewById(R.id.get_mainData_editText);
        noteContract.noteEntry entry = new noteContract.noteEntry();

        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy");
        String finalDate = format.format(date);

        finalDate += "\n";

        format = new SimpleDateFormat("hh:mm a");
        finalDate += format.format(date);

        byte b[] = text.getText().toString().getBytes();
        ContentValues values = new ContentValues();
        values.put(entry.TABLE_COLUMN_ACTUAL_DATA,text.getText().toString());
        values.put(entry.TABLE_COLUMN_TIME,time);
        values.put(entry.TABLE_COLUMN_PRIORITY,priority);
        values.put(entry.TABLE_COLUMN_COLOR,color);
        values.put(entry.TABLE_COLUMN_SIZE,b.length);
        values.put(entry.TABLE_COLUMN_DATE,finalDate);

        Uri uri = getContentResolver().insert(entry.contentUri,values);


        if(uri == null){
            Toast.makeText(getApplicationContext(),"unable to insert",Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(getApplicationContext(),"Inserted successfully",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    void setValues(){
        noteContract.noteEntry entry = new noteContract.noteEntry();
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);

        if(cursor.moveToFirst() && cursor.isFirst()){
        ((EditText)findViewById(R.id.get_mainData_editText)).setText(

                cursor.getString(cursor.getColumnIndex(entry.TABLE_COLUMN_ACTUAL_DATA))

        );
        ((RelativeLayout)findViewById(R.id.get_main_data_relativeLayout)).setBackgroundColor(Color.parseColor(

                cursor.getString(cursor.getColumnIndex(entry.TABLE_COLUMN_COLOR))

        ));
            color = cursor.getString(cursor.getColumnIndex(entry.TABLE_COLUMN_COLOR));

            priority = cursor.getInt(cursor.getColumnIndex(entry.TABLE_COLUMN_PRIORITY));
        }
    }

    void upDateExistingInfo(){

        EditText text = (EditText)findViewById(R.id.get_mainData_editText);
        noteContract.noteEntry entry = new noteContract.noteEntry();

        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy");
        String finalDate = format.format(date);

        finalDate += "\n";

        format = new SimpleDateFormat("hh:mm a");
        finalDate += format.format(date);

        byte b[] = text.getText().toString().getBytes();
        ContentValues values = new ContentValues();
        values.put(entry.TABLE_COLUMN_ACTUAL_DATA,text.getText().toString());
        values.put(entry.TABLE_COLUMN_TIME,time);
        values.put(entry.TABLE_COLUMN_PRIORITY,priority);
        values.put(entry.TABLE_COLUMN_COLOR,color);
        values.put(entry.TABLE_COLUMN_SIZE,b.length);
        values.put(entry.TABLE_COLUMN_DATE,finalDate);

        int i = getContentResolver().update(uri,values,null,null);
        if(i != -1){
            Toast.makeText(getApplicationContext(),"Updated successfully",Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"Unable to update",Toast.LENGTH_SHORT).show();
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit without save", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
