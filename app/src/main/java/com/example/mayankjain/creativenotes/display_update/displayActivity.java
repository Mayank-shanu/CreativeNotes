package com.example.mayankjain.creativenotes.display_update;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;


import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mayankjain.creativenotes.R;
import com.example.mayankjain.creativenotes.adapters.cursorAdapter;
import com.example.mayankjain.creativenotes.noteContract;

import static android.R.attr.data;
import static android.content.ContentValues.TAG;


public class displayActivity extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    String TAG = "trekking latest";
    cursorAdapter adapter;
    int loaderID = 1;
    View getView;
    Boolean booleanToChkDel = true;
    int priority;
    Boolean search = false;
    Boolean visible =false;
    Cursor cursorBefore = null;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display,container,false);
        getView = view;
        noteContract.noteEntry en = new noteContract.noteEntry();
        if(getArguments() != null){
            priority = getArguments().getInt("_priority");
        }
        else priority = en.PRIORITY_NORMAL;


        ListView listView = (ListView) getView.findViewById(R.id.main_display_fragment_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),to_get_diisplay_data.class);
                intent.setData(ContentUris.withAppendedId((new noteContract.noteEntry()).contentUri,id));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                String[] arr = new String[]{"Set/Alter Priority","Delete","Share","Dismiss"};
                AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
                View longClickView = LayoutInflater.from(getActivity()).inflate(R.layout.long_click_dialogbox_layout,null);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
                ListView listView_long_click = (ListView) longClickView.findViewById(R.id.long_click_dialog_listView);
                listView_long_click.setAdapter(adapter);
                builder.setView(longClickView);
                builder.setCancelable(true);
                final AlertDialog dialog = builder.create();
                dialog.show();


                listView_long_click.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id_long_click) {
                        if(position == 0 /*For fav*/){
                            dialog.dismiss();
                            acnPriority(id);

                        }
                        else if(position == 1/* Delete*/){
                            acnDelete(id);
                            dialog.dismiss();
                        }
                        else if(position == 2/*Share*/){
                            acnShare(id);
                            dialog.dismiss();
                        }
                        else if(position == 3/*Dismiss*/){
                            dialog.dismiss();
                        }
                    }
                });

                return true;
            }
        });

        ((ImageView)getView.findViewById(R.id.search_imageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!visible)
                {
                    ImageView imageView = ((ImageView)getView.findViewById(R.id.search_imageView));
                    imageView.setBackgroundColor(Color.parseColor("#3e2723"));

                    search = true;
                    ((EditText)getView.findViewById(R.id.search_editText)).setVisibility(View.VISIBLE);
                    visible = true;
                }

                else
                {   if (adapter != null && cursorBefore != null)
                    adapter.swapCursor(cursorBefore);

                    ImageView imageView = ((ImageView)getView.findViewById(R.id.search_imageView));
                    imageView.setBackground(getResources().getDrawable(R.drawable.search_icon));
                    imageView.setBackgroundColor(Color.parseColor("#ffffff"));

                    search = false;
                    ((EditText)getView.findViewById(R.id.search_editText)).setVisibility(View.GONE);
                    visible = false;
                }
            }
        });


        final EditText editText = (EditText)view.findViewById(R.id.search_editText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteContract.noteEntry entry = new noteContract.noteEntry();
                String sData = editText.getText().toString();
                if(priority == entry.PRIORITY_URGENT) {
                    Cursor cursor = getActivity().getContentResolver().query(
                            entry.contentUri,
                            null, entry.TABLE_COLUMN_ACTUAL_DATA + " LIKE " + "'%" + sData + "%'"
                            +" AND " + entry.TABLE_COLUMN_PRIORITY + " =" + priority
                            ,
                            null,
                            entry.TABLE_COLUMN_TIME + " DESC");
                    if(cursorBefore != null && adapter != null)
                    adapter.swapCursor(cursor);
                }

                else {
                    Cursor cursor = getActivity().getContentResolver().query(
                            entry.contentUri,
                            null, entry.TABLE_COLUMN_ACTUAL_DATA + " LIKE " + "'%" + sData + "%'"
                            +" AND (" + entry.TABLE_COLUMN_PRIORITY + " =" + entry.PRIORITY_NORMAL+
                            " OR " + entry.TABLE_COLUMN_PRIORITY + " =" + entry.PRIORITY_FAVOURITE + ")"
                            ,
                            null,
                            entry.TABLE_COLUMN_TIME + " DESC");
                    if(cursorBefore != null && adapter != null)
                    adapter.swapCursor(cursor);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getLoaderManager().initLoader(loaderID,null,this);


        return view;
    }

    void acnDelete(final long id){
        booleanToChkDel = true;
        final Snackbar snackbar = Snackbar.make(getView,"Deletion in progress",Snackbar.LENGTH_SHORT);
        snackbar.setAction("Cancel Delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Canceled Delete",Toast.LENGTH_SHORT).show();
                booleanToChkDel = false;
                snackbar.dismiss();
            }
        });
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if(booleanToChkDel) {
                    noteContract.noteEntry entry = new noteContract.noteEntry();
                    Uri uri = ContentUris.withAppendedId(entry.contentUri, id);
                    int num = getActivity().getContentResolver().delete(uri, null, null);
                    if (num != -1 && num != 0) {
                        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Unable to delete id:" + id + " number " + num, Toast.LENGTH_SHORT).show();
                }

            }
        });
       snackbar.show();


    }

    void acnShare(final long id){

        Cursor cursor = getActivity().getContentResolver().query(ContentUris.withAppendedId((new noteContract.noteEntry()).contentUri,id),null,null,null,null);
        cursor.moveToFirst();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TEXT,cursor.getString(cursor.getColumnIndex((new noteContract.noteEntry()).TABLE_COLUMN_ACTUAL_DATA)));
        startActivity(Intent.createChooser(intent,"Share"));

    }

    void acnPriority(final long id){
        Cursor cursor = getActivity().getContentResolver().query(ContentUris.withAppendedId(((new noteContract.noteEntry()).contentUri),id),null,null,null,null);
        if(cursor.moveToFirst()&& cursor.isFirst()) {
            String title = null;
            final noteContract.noteEntry entry = new noteContract.noteEntry();
            int pri = cursor.getInt(cursor.getColumnIndex((new noteContract.noteEntry()).TABLE_COLUMN_PRIORITY));
            if (pri == entry.PRIORITY_NORMAL)
                title = "Current priority is Normal";
            else if (pri == entry.PRIORITY_URGENT)
                title = "Current priority is Urgent";
            else if (pri == entry.PRIORITY_FAVOURITE)
                title = "Current priority is Favourite";

            String[] arr = new String[]{"Normal", "Favourite", "Urgent"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setItems(arr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int finalPriority = entry.PRIORITY_NORMAL;

                    if(which == entry.PRIORITY_NORMAL)
                        finalPriority = entry.PRIORITY_NORMAL;

                    else if(which == entry.PRIORITY_FAVOURITE)
                        finalPriority = entry.PRIORITY_FAVOURITE;

                    else if(which == entry.PRIORITY_URGENT)
                        finalPriority = entry.PRIORITY_URGENT;
                    ContentValues values = new ContentValues();
                    values.put(entry.TABLE_COLUMN_PRIORITY,finalPriority);
                    int result = getActivity().getContentResolver().update(ContentUris.withAppendedId(entry.contentUri,id),values,null,null);

                    if (result!=-1) Toast.makeText(getActivity(),"Changed Priority",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getActivity(),"Unable To Change Priority Priority",Toast.LENGTH_SHORT).show();

                }
            });
            builder.setTitle(title);
            builder.show();
        }
    }

    void updateUi(Cursor cursor){
        cursor.moveToFirst();
        int chk = cursor.getCount();
        if(chk == 0) {


            LinearLayout linearLayout = (LinearLayout) getView.findViewById(R.id.main_display_fragment_no_content_linearLayout);
            linearLayout.setVisibility(View.VISIBLE);

            ListView listView = (ListView) getView.findViewById(R.id.main_display_fragment_listView);
            listView.setVisibility(View.GONE);

        }
        else{
            LinearLayout linearLayout = (LinearLayout) getView.findViewById(R.id.main_display_fragment_no_content_linearLayout);
            linearLayout.setVisibility(View.GONE);

            ListView listView = (ListView) getView.findViewById(R.id.main_display_fragment_listView);
            listView.setVisibility(View.VISIBLE);
            adapter = new cursorAdapter(getActivity(),cursor);
            listView.setAdapter(adapter);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        noteContract.noteEntry entry = new noteContract.noteEntry();

            if (priority == entry.PRIORITY_NORMAL) {
                Log.e("trekking cursor", "normal called");
                return new CursorLoader(getActivity(), (new noteContract.noteEntry()).contentUri, null,

                        entry.TABLE_COLUMN_PRIORITY + " =" + entry.PRIORITY_NORMAL + " OR "
                                + entry.TABLE_COLUMN_PRIORITY + " =" + entry.PRIORITY_FAVOURITE
                        , null, (new noteContract.noteEntry()).TABLE_COLUMN_TIME + " DESC");
            } else if (priority == entry.PRIORITY_FAVOURITE) {
                Log.e("trekking cursor", "Fav called");
                return new CursorLoader(getActivity(), entry.contentUri, null, entry.TABLE_COLUMN_PRIORITY + " =" + priority, null, entry.TABLE_COLUMN_TIME + " DESC");

            } else if (priority == entry.PRIORITY_URGENT) {
                Log.e("trekking cursor", "urg called");
                return new CursorLoader(getActivity(), entry.contentUri, null, entry.TABLE_COLUMN_PRIORITY + " =" + priority, null, entry.TABLE_COLUMN_TIME + " DESC");

            }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        updateUi(data);

        if(cursorBefore == null)
            cursorBefore = data;

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        if(priority == (new noteContract.noteEntry()).PRIORITY_NORMAL )
        adapter.swapCursor(null);
        Log.e(TAG, "onLoaderReset: called");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: called with cursor value");
    }
}
//https://drive.google.com/open?id=0B4u1IlfqwuvPbU1YNFJUNmoySTg