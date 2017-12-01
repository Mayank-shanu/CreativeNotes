package com.example.mayankjain.creativenotes.update_color_dataBase;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.database.colorDatabase;
import com.example.mayankjain.creativenotes.noteContract;

import static android.R.attr.button;

public class enter_to_update_color extends AppCompatActivity {
    Boolean booleanToChkDel = true;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_to_update_color);
        Intent intent = getIntent();
        id = intent.getLongExtra("_id",-1);

        Button button = ((Button)findViewById(R.id.button_color_to_select));

        if(id != -1) updateUi();
        else {
            button.setBackground(getResources().getDrawable(R.drawable.un_checked));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ((EditText)findViewById(R.id.editText_color_to_select)).getText().toString();
                if(code.length() == 6)
                {
                    try{
                        ((TextView)findViewById(R.id.display_color_to_select)).setBackgroundColor(Color.parseColor(
                                "#" +code
                        ));
                        ((Button)findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.checked));
                    }
                    catch (java.lang.NumberFormatException e){
                        Toast.makeText(getApplicationContext(),"Entered wrong value",Toast.LENGTH_SHORT).show();
                        ((Button)findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.un_checked));
                    }
                }
                else {
                    ((Button)findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.un_checked));
                    ((TextView)findViewById(R.id.display_color_to_select)).setBackgroundColor(Color.parseColor(
                            "#FFFFFF"
                    ));
                    Toast.makeText(getApplicationContext(),"Entered Value is Wrong",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.enter_to_udte_color,menu);
        if(id == -1){
        MenuItem item = menu.findItem(R.id.action_delete_color);
            item.setVisible(false);

            item = menu.findItem(R.id.action_share_color);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_save_color){
            if(id == -1)
                acnSave();
            else
                acnUpdate(id);
        }
        else if(item.getItemId() == R.id.action_delete_color){

            acnDelete(id);

        }
        else if(item.getItemId() == R.id.action_share_color){

            acnShare(id);

        }
        else if(item.getItemId() == R.id.action_help_color){

            String url = "http://htmlcolorcodes.com/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    void acnDelete(final long id){
        booleanToChkDel = true;
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Deletion in progress",Snackbar.LENGTH_SHORT);
        snackbar.setAction("Cancel Delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Canceled Delete",Toast.LENGTH_SHORT).show();
                booleanToChkDel = false;
                snackbar.dismiss();
            }
        });
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if(booleanToChkDel) {

                    colorDatabase db = new colorDatabase(getApplicationContext());
                    SQLiteDatabase database = db.getReadableDatabase();
                    int res =database.delete(noteContract.colorEntry.TABLE_NAME,noteContract.colorEntry._ID +" =" +id,null);
                    if(res != 0){
                        Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }
        });
        snackbar.show();


    }

    void acnShare(final long id){

        if(id != -1) {
            colorDatabase db = new colorDatabase(getApplicationContext());
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.query(noteContract.colorEntry.TABLE_NAME, null, noteContract.colorEntry._ID + " =" + id, null, null, null, null);
            cursor.moveToFirst();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_TEXT,

                    cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_NAME))
                            + "   " +
                            cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE))

            );
            startActivity(Intent.createChooser(intent, "Share"));
        }
    }

    void acnSave(){

        colorDatabase db = new colorDatabase(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();
        ContentValues values = new ContentValues();

        String colorCode = ((EditText)findViewById(R.id.editText_color_to_select)).getText().toString().trim();
        Button button = ((Button)findViewById(R.id.button_color_to_select));
        String colorName = ((EditText)findViewById(R.id.name_color_to_select)).getText().toString().trim();

        if(colorCode.length() == 6 && !colorName.isEmpty()) {

            try{
                ((TextView)findViewById(R.id.display_color_to_select)).setBackgroundColor(Color.parseColor(
                        "#" +colorCode
                ));
                ((Button)findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.checked));
            }
            catch (java.lang.NumberFormatException e){
                Toast.makeText(getApplicationContext(),"Entered wrong value",Toast.LENGTH_SHORT).show();
                ((Button)findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.un_checked));
                return;
            }

            values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,colorName);
            values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#"+colorCode);
            long res = database.insert(noteContract.colorEntry.TABLE_NAME, null, values);
            if(res != -1){
                Toast.makeText(getApplicationContext(),"Added Color",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else {
            if(colorCode.length()!=6)
                Toast.makeText(getApplicationContext(),"Entered wrong value",Toast.LENGTH_SHORT).show();
            if(colorName.isEmpty())
                Toast.makeText(getApplicationContext(),"Enter Name Of color",Toast.LENGTH_SHORT).show();
        }
    }

    void acnUpdate(final long id){
        colorDatabase db = new colorDatabase(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();
        ContentValues values = new ContentValues();

        String colorCode = ((EditText)findViewById(R.id.editText_color_to_select)).getText().toString().trim();
        Button button = ((Button)findViewById(R.id.button_color_to_select));
        String colorName = ((EditText)findViewById(R.id.name_color_to_select)).getText().toString().trim();
        if(colorCode.length() == 6 && !colorName.isEmpty()) {

            try{
                ((TextView)findViewById(R.id.display_color_to_select)).setBackgroundColor(Color.parseColor(
                        "#" +colorCode
                ));
                ((Button)findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.checked));
            }
            catch (java.lang.NumberFormatException e){
                Toast.makeText(getApplicationContext(),"Entered wrong value",Toast.LENGTH_SHORT).show();
                ((Button)findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.un_checked));
                return;
            }

            values.put(noteContract.colorEntry.TABLE_COLUMN_NAME,colorName);
            values.put(noteContract.colorEntry.TABLE_COLUMN_VALUE,"#"+colorCode);

            long res = database.update(noteContract.colorEntry.TABLE_NAME,values,noteContract.colorEntry._ID +" =" + id,null);
            if(res != -1){
                Toast.makeText(getApplicationContext(),"Updated Color",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else {
            if(colorCode.length()!=6)
                Toast.makeText(getApplicationContext(),"Entered wrong value",Toast.LENGTH_SHORT).show();
            if(colorName.isEmpty())
                Toast.makeText(getApplicationContext(),"Enter Name Of color",Toast.LENGTH_SHORT).show();
        }
    }

    void updateUi(){
        colorDatabase db = new colorDatabase(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(noteContract.colorEntry.TABLE_NAME,null,
                noteContract.colorEntry._ID +" =" + id,
                null,null,null,null);
        if(cursor.moveToFirst() && cursor.isFirst()) {
            ((TextView) findViewById(R.id.display_color_to_select)).setBackgroundColor(Color.parseColor(

                    cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE))

            ));

            ((EditText) findViewById(R.id.editText_color_to_select)).setText(

                    cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE)).substring(1,7)
            );
            ((Button) findViewById(R.id.button_color_to_select)).setBackground(getResources().getDrawable(R.drawable.checked));

            ((EditText) findViewById(R.id.name_color_to_select)).setText(

                    cursor.getString(cursor.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_NAME))
            );
        }

    }



}
