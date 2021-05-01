package com.asbelogur.notimanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "DatabaseHelper";

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notifications";

    private static final String TABLE_MESSAGES = "messages";
    private static final String COLUMN_ID = "_id";
    private static final String PACKAGE_NAME = "package_name";
    private static final String APP_NAME = "app_name";
    private static final String USER = "user";
    private static final String CONTENT = "content";
    private static final String POST_TIME = "post_time";
    private static final String CHANEL_ID = "chanel_id";
    private static final String GROUP_KEY = "group_id";
    private static final String NOTIFICATION_ID = "notification_id";


    /*
    private static final String TABLE_LARGE_ICONS = "large_icons";
    private static final String COLUMN_ID2 = "_id";
    private static final String LARGE_ICON_PATH = "path";

     */


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_MESSAGES +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PACKAGE_NAME + " TEXT, " +
                APP_NAME + " TEXT, " +
                USER + " TEXT, " +
                CONTENT + " TEXT, " +
                POST_TIME + " TEXT, " +
                CHANEL_ID + " TEXT, " +
                GROUP_KEY + " TEXT, " +
                NOTIFICATION_ID + " INTEGER);";
        db.execSQL(query1);

        /*
        String query2 = "CREATE TABLE " + TABLE_MESSAGES +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GROUP_KEY + " IMAGE);";
        db.execSQL(query2);

         */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public void addMessage(String packageName, String appName, String user, String content, String post_time, String chanel_id, String group_key, int notification_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PACKAGE_NAME, packageName);
        cv.put(APP_NAME, appName);
        cv.put(USER, user);
        cv.put(CONTENT, content);
        cv.put(POST_TIME, post_time);
        cv.put(CHANEL_ID, chanel_id);
        cv.put(GROUP_KEY, group_key);
        cv.put(NOTIFICATION_ID, notification_id);

        db.insert(TABLE_MESSAGES, null, cv);

        Log.i(LOGTAG, "A notification was added to the database");
    }

    public Cursor readNotifications() {
        String query = "SELECT * FROM " + TABLE_MESSAGES;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
            cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getUniqueAppsNames() {
        String query = "SELECT * FROM " + TABLE_MESSAGES + " GROUP BY " + APP_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null)
            cursor = db.rawQuery(query, null);

        return cursor;
    }
}
