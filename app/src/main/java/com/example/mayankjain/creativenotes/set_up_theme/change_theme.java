package com.example.mayankjain.creativenotes.set_up_theme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.adapters.colorsAdapter;
import com.example.mayankjain.creativenotes.database.colorDatabase;
import com.example.mayankjain.creativenotes.noteContract;

import java.util.Set;

public class change_theme extends AppCompatActivity {
    SharedPreferences.Editor editor;
    String colorN;
    String colorS;
    String colorB;
    String colorT;
    String colorA;
    String colorAT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theme);
        setTitle("Settings");

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(noteContract.userThemePreferences.sharePrefName, Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    private void dialogBox(final int ID, final TextView textView){

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
                String color;
                String[] selnArg = new String[]{"" + id};
                Cursor c = database.query(noteContract.colorEntry.TABLE_NAME,null,
                        noteContract.colorEntry._ID +"=?",
                        selnArg,
                        null,
                        null,
                        null
                );
                c.moveToFirst();
                color = c.getString(c.getColumnIndex(noteContract.colorEntry.TABLE_COLUMN_VALUE)) ;
                c.close();
                dialog.dismiss();

                textView.setBackgroundColor(Color.parseColor(color));

                if(ID == R.id.onClickBackgroundButton) colorB = color;
                else if(ID == R.id.onClickNavigationButton) colorN = color;
                else if(ID == R.id.onClickTextButton) colorT = color;
                else if(ID == R.id.onClickStatusButton) colorS = color;
                else if(ID == R.id.onClickActionButton) colorA = color;
                else if(ID == R.id.onClickActionTextButton) colorAT = color;

            }
        });


    }

    public void onClickNavigation(View v){
        CheckBox checkBox = (CheckBox)findViewById(R.id.onClickNavigationCheckBox);
        TextView textView =(TextView)findViewById(R.id.onClickNavigation);
        dialogBox(v.getId(),textView);

    }

    public void onClickStatus(View v){

        CheckBox checkBox = (CheckBox)findViewById(R.id.onClickStatusCheckBox);
        TextView textView =(TextView)findViewById(R.id.onClickStatus);
        dialogBox(v.getId(),textView);
    }

    public void onClickBackground(View v){
        CheckBox checkBox = (CheckBox)findViewById(R.id.onClickBackgroundCheckBox);
        TextView textView =(TextView)findViewById(R.id.onClickBackground);
        dialogBox(v.getId(),textView);
    }

    public void onClickText(View v){

        CheckBox checkBox = (CheckBox)findViewById(R.id.onClickTextCheckBox);
        TextView textView =(TextView)findViewById(R.id.onClickText);
        dialogBox(v.getId(),textView);
    }

    public void onCLickCheckbox(View v){
        int id = v.getId();
        CheckBox checkBox = (CheckBox) v;
        if(id == R.id.onClickStatusCheckBox){
           if(checkBox.isChecked()){
               ((Button)findViewById(R.id.onClickStatusButton)).setVisibility(View.VISIBLE);
               ((TextView)findViewById(R.id.onClickStatus)).setVisibility(View.VISIBLE);
           }
           else{
               ((Button)findViewById(R.id.onClickStatusButton)).setVisibility(View.GONE);
               ((TextView)findViewById(R.id.onClickStatus)).setVisibility(View.GONE);
           }
        }
        else if(id == R.id.onClickNavigationCheckBox){

            if(checkBox.isChecked()){
                ((Button)findViewById(R.id.onClickNavigationButton)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.onClickNavigation)).setVisibility(View.VISIBLE);
            }
            else{
                ((Button)findViewById(R.id.onClickNavigationButton)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.onClickNavigation)).setVisibility(View.GONE);
            }

        }
        else if(id == R.id.onClickBackgroundCheckBox){

            if(checkBox.isChecked()){
                ((Button)findViewById(R.id.onClickBackgroundButton)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.onClickBackground)).setVisibility(View.VISIBLE);
            }
            else{
                ((Button)findViewById(R.id.onClickBackgroundButton)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.onClickBackground)).setVisibility(View.GONE);
            }
        }
        else if(id == R.id.onClickTextCheckBox){

            if(checkBox.isChecked()){
                ((Button)findViewById(R.id.onClickTextButton)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.onClickText)).setVisibility(View.VISIBLE);
            }
            else{
                ((Button)findViewById(R.id.onClickTextButton)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.onClickText)).setVisibility(View.GONE);
            }

        }
        else if(id == R.id.onClickActionCheckBox){

            if(checkBox.isChecked()){
                ((Button)findViewById(R.id.onClickActionButton)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.onClickAction)).setVisibility(View.VISIBLE);
            }
            else{
                ((Button)findViewById(R.id.onClickActionButton)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.onClickAction)).setVisibility(View.GONE);
            }

        }
        else if(id == R.id.onClickActionTextCheckBox){

            if(checkBox.isChecked()){
                ((Button)findViewById(R.id.onClickActionTextButton)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.onClickActionText)).setVisibility(View.VISIBLE);
            }
            else{
                ((Button)findViewById(R.id.onClickActionTextButton)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.onClickActionText)).setVisibility(View.GONE);
            }

        }


    }

    public  void  onClickAction(View v ){
        TextView textView =(TextView)findViewById(R.id.onClickAction);
        dialogBox(v.getId(),textView);
    }

    public void onClickActionText(View v){
        TextView textView =(TextView)findViewById(R.id.onClickActionText);
        dialogBox(v.getId(),textView);
    }

    void onClickSave(){
        editor.clear();

        if(((CheckBox)findViewById(R.id.onClickNavigationCheckBox)).isChecked()){
             if(colorN != null){
                 editor.putString(noteContract.userThemePreferences.NAVIGATION_BAR_COLOR,colorN);
             }
             else{
                 Toast.makeText(getApplicationContext(),"select color for navigation bar",Toast.LENGTH_SHORT).show();
                 return;
             }
        }
        if(((CheckBox)findViewById(R.id.onClickBackgroundCheckBox)).isChecked()){

                if(colorB != null){
                    editor.putString(noteContract.userThemePreferences.LIST_BACKGROUND,colorB);
                }
                else{
                    Toast.makeText(getApplicationContext(),"select color for background ",Toast.LENGTH_SHORT).show();
                    return;
                }

        }
        if(((CheckBox)findViewById(R.id.onClickStatusCheckBox)).isChecked()){

                if(colorS != null){
                    editor.putString(noteContract.userThemePreferences.STATUS_BAR_COLOR,colorS);
                }
                else{
                    Toast.makeText(getApplicationContext(),"select color for status bar",Toast.LENGTH_SHORT).show();
                    return;
                }

        }
        if(((CheckBox)findViewById(R.id.onClickTextCheckBox)).isChecked()){

                if(colorT != null){
                    editor.putString(noteContract.userThemePreferences.TEXT_COLOR,colorT);
                }
                else{
                    Toast.makeText(getApplicationContext(),"select color for Text",Toast.LENGTH_SHORT).show();
                    return;
                }

        }
        if(((CheckBox)findViewById(R.id.onClickActionCheckBox)).isChecked()){

            if(colorA != null){
                editor.putString(noteContract.userThemePreferences.ACTION_BAR,colorA);
            }
            else{
                Toast.makeText(getApplicationContext(),"select color for Action Bar",Toast.LENGTH_SHORT).show();
                return;
            }

        }
        if(((CheckBox)findViewById(R.id.onClickTextCheckBox)).isChecked()){

            if(colorAT != null){
                editor.putString(noteContract.userThemePreferences.ACTION_BAR_TEXT,colorAT);
            }
            else{
                Toast.makeText(getApplicationContext(),"select color for Action Bar Text",Toast.LENGTH_SHORT).show();
                return;
            }

        }
        editor.putBoolean(noteContract.userThemePreferences.TO_CHECK_CHANGED,false);
        Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();
        editor.commit();
        editor.apply();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.for_change_theme,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_theme_save){
            onClickSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
