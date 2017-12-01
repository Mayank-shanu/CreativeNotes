package com.example.mayankjain.creativenotes.display_update;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.noteContract;

public class to_display_fav_urg extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_show_fav_and_urgent);

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

        Fragment fragment = new displayActivity();

        noteContract.noteEntry entry = new noteContract.noteEntry();
        int priorityRec = getIntent().getIntExtra("_priority",entry.PRIORITY_FAVOURITE);

        if(priorityRec == entry.PRIORITY_FAVOURITE)
            setTitle("Your Favourite Notes");

        else if(priorityRec == entry.PRIORITY_URGENT)
            setTitle("Your Urgent Notes");


        Bundle bundle = new Bundle();
        bundle.putInt("_priority",priorityRec);

        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.main_content_linearLayout,fragment,"fav_urg_search").commit();

    }
}
