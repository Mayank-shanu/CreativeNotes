package com.example.mayankjain.creativenotes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.mayankjain.creativenotes.about.aboutActivity;
import com.example.mayankjain.creativenotes.display_update.to_display_fav_urg;
import com.example.mayankjain.creativenotes.display_update.to_get_diisplay_data;
import com.example.mayankjain.creativenotes.set_up_theme.change_theme;
import com.example.mayankjain.creativenotes.update_color_dataBase.updateColorsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(noteContract.userThemePreferences.sharePrefName, Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), to_get_diisplay_data.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setBackgroundColor(Color.parseColor("#d7cccb"));
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),change_theme.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        noteContract.noteEntry entry= new noteContract.noteEntry();

        if (id == R.id.nav_About) {
            Intent intent = new Intent(getApplicationContext(), aboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_color) {

            Intent intent = new Intent(getApplicationContext(),updateColorsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_Urgent) {

            Intent intent = new Intent(getApplicationContext(),to_display_fav_urg.class);
            intent.putExtra("_priority", entry.PRIORITY_URGENT );
            startActivity(intent);

        } else if (id == R.id.nav_Favourite) {

            Intent intent = new Intent(getApplicationContext(),to_display_fav_urg.class);
            intent.putExtra("_priority", entry.PRIORITY_FAVOURITE );
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            //https://drive.google.com/open?id=0B4u1IlfqwuvPbU1YNFJUNmoySTg

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"https://drive.google.com/open?id=0B4u1IlfqwuvPbU1YNFJUNmoySTg");
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(noteContract.userThemePreferences.sharePrefName, Context.MODE_PRIVATE);
        if(!(preferences.getBoolean(noteContract.userThemePreferences.TO_CHECK_CHANGED,true))){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(noteContract.userThemePreferences.TO_CHECK_CHANGED);
            editor.putBoolean(noteContract.userThemePreferences.TO_CHECK_CHANGED,true);
            editor.apply();
            editor.commit();
        }

    }
}
