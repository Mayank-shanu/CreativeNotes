package com.example.mayankjain.creativenotes;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import static android.R.attr.contentAuthority;

public final class noteContract {

    public static String content_Authority = "com.example.mayankjain.creativenotes";
    public static String content_Path = "my_notes";
    public static Uri baseUri = Uri.parse("content://" + content_Authority );

    public final static class noteEntry implements BaseColumns{

        public String TABLE_NAME = "my_notes";
        public String TABLE_COLUMN_ACTUAL_DATA ="actual_data";
        public String TABLE_COLUMN_COLOR ="color";
        public String TABLE_COLUMN_TIME ="time";
        public String TABLE_COLUMN_DATE ="date";
        public String TABLE_COLUMN_PRIORITY ="priority";
        public String TABLE_COLUMN_SIZE ="size";
        public String TABLE_COLUMN_ID = BaseColumns._ID;

        public int PRIORITY_NORMAL = 0;
        public int PRIORITY_FAVOURITE = 1;
        public int PRIORITY_URGENT = 2;
        public int SEARCH = 3;


        public int NOTE = 101;
        public int NOTE_ID = 102;


        public Uri contentUri = Uri.withAppendedPath(baseUri,content_Path);

        public UriMatcher uriMatch(){

            UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
            matcher.addURI(content_Authority , content_Path,NOTE);
            matcher.addURI(content_Authority , content_Path + "/#",NOTE_ID);
            return matcher;
        }

    }

    public final static class colorEntry implements BaseColumns{
        public static String TABLE_NAME = "colors";
        public static String TABLE_COLUMN_NAME = "name";
        public static String TABLE_COLUMN_VALUE = "value";
        public static String _ID = BaseColumns._ID;
    }

    public  final static class userThemePreferences{
        public static final String sharePrefName = "userPref";
        public static final String NAVIGATION_BAR_COLOR = "nav_bar";
        public static final String STATUS_BAR_COLOR = "stat_bar";
        public static final String LIST_BACKGROUND = "back";
        public static final String TEXT_COLOR ="txtColor";
        public static final String ACTION_BAR = "acn_bar";
        public static final String ACTION_BAR_TEXT = "acn_text";
        public static final String TO_CHECK_CHANGED ="chk";
    }

}
