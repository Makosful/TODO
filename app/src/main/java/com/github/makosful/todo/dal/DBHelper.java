package com.github.makosful.todo.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.makosful.todo.bll.notifications.NotificationContract;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todoactivities.db";

    private static final int DATABASE_VERSION = 0;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the reminder table
        String SQL_CREATE_NOTICE_TABLE =  "CREATE TABLE " + NotificationContract.NotificationEntry.TABLE_NAME + " ("
                + NotificationContract.NotificationEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NotificationContract.NotificationEntry.TITLE + " TEXT NOT NULL, "
                + NotificationContract.NotificationEntry.DATE + " TEXT NOT NULL, "
                + NotificationContract.NotificationEntry.TIME + " TEXT NOT NULL, "
                + NotificationContract.NotificationEntry.IMPORTANCE + " TEXT NOT NULL, "
                + NotificationContract.NotificationEntry.DESCRIPTION + " TEXT, "
                + NotificationContract.NotificationEntry.ACTIVE + " TEXT NOT NULL " + " );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_NOTICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // we won't be upgrading the DB. Here because of super.
    }
}
