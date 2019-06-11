package com.github.makosful.todo.dal.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.be.Notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeSQLite implements IStorage<Notice> {
    private static final String TAG = "NoticeSQLite";

    private static final String TABLE_NAME = "notice_table";
    private static final String FIELD_ID = "id";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_IMPORTANCE = "importance";
    private static final String FIELD_THUMBNAIL = "thumbnail";
    private static final String FIELD_ICON = "icon";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_DATE = "date";

    private final String[] columns = new String[] {
            FIELD_ID,
            FIELD_TITLE,
            FIELD_IMPORTANCE,
            FIELD_THUMBNAIL,
            FIELD_ICON,
            FIELD_DESCRIPTION,
            FIELD_DATE
    };

    private final SQLiteDatabase database;

    public NoticeSQLite(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        this.database = openHelper.getWritableDatabase();

        //Seeding database
        //seed();
    }

    @Override
    public Notice create(Notice notice) {
        Log.d(TAG, "create: Saving new Notice to the database");

        final String s = "INSERT INTO " +
                TABLE_NAME + "(" +
                    FIELD_TITLE + ", " +
                    FIELD_IMPORTANCE + ", " +
                    FIELD_THUMBNAIL + ", " +
                    FIELD_ICON + ", " +
                    FIELD_DESCRIPTION + ", " +
                    FIELD_DATE + "" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        final SQLiteStatement statement = this.database.compileStatement(s);

        int i = 0;

        Log.d(TAG, "create: Saving Title");
        final String title = notice.getTitle();
        if (title == null || title.isEmpty()) {
            statement.bindNull(++i);
            Log.d(TAG, "create: No title found");
        } else {
            statement.bindString(++i, title);
            Log.d(TAG, "create: Title saved");
        }

        Log.d(TAG, "create: Saving importance");
        final String importance = notice.getImportance();
        if (importance == null || importance.isEmpty()) {
            statement.bindNull(++i);
            Log.d(TAG, "create: No importance found");
        } else {
            statement.bindString(++i, importance);
        }
        Log.d(TAG, "create: Saving thumbnail");
        final String thumbnailUrl = notice.getThumbnailUrl();
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            statement.bindNull(++i);
            Log.d(TAG, "create: No thumbnail found");
        } else {
            statement.bindString(++i, thumbnailUrl);
            Log.d(TAG, "create: Thumbnail saved");
        }

        Log.d(TAG, "create: Saving Icon");
        final String iconUrl = notice.getIconUrl();
        if (iconUrl == null || iconUrl.isEmpty()) {
            statement.bindNull(++i);
            Log.d(TAG, "create: No icon found");
        } else {
            statement.bindString(++i, iconUrl);
            Log.d(TAG, "create: Icon saved");
        }

        Log.d(TAG, "create: Saving description");
        final String description = notice.getDescription();
        if (description == null || description.isEmpty()) {
            statement.bindNull(++i);
            Log.d(TAG, "create: No description found");
        } else {
            statement.bindString(++i, description);
            Log.d(TAG, "create: Description saved");
        }

        Log.d(TAG, "create: Saving time");
        if (notice.getDateAndTime() == null) {
            statement.bindNull(++i);
            Log.d(TAG, "create: No time found");
        } else {
            final long time = notice.getDateAndTime().getTime();
            statement.bindLong(++i, time);
            Log.d(TAG, "create: Time saved");
        }

        Log.d(TAG, "create: Executing statement");
        final long result = statement.executeInsert();

        if (result < 0) {
            return null;
        } else {
            notice.setId((int) result);
            return notice;
        }
    }

    @Override
    public Notice read(int id) {
        Log.d(TAG, "read: Reading the Notice with the ID: " + id);

        Cursor cursor = this.database.query(
                TABLE_NAME, columns, FIELD_ID + " = ?", new String[] {"" + id},
                null,null, FIELD_ID);

        Log.d(TAG, "read: Query sent. Results received");

        Notice notice;

        if (cursor.moveToFirst()) {
            notice = assembleNotice(cursor);
        } else {
            notice = null;
        }

        // Cleanup
        if (!cursor.isClosed()) cursor.close();

        return notice;
    }

    @Override
    public List<Notice> readAll() {
        Log.d(TAG, "readAll: Attempt to read all the Notices saved");

        final List<Notice> notices = new ArrayList<>();

        final Cursor cursor = this.database.query(
                TABLE_NAME, columns, null, null, null, null, FIELD_ID);

        Log.d(TAG, "readAll: Query sent. Results received");

        // Moves the cursor to the first entry
        if (cursor.moveToFirst()) {
            Log.d(TAG, "readAll: Starts looping through the results");
            do {
                notices.add(assembleNotice(cursor));
            } while (cursor.moveToNext()); // Continues until there are no more entries
            Log.d(TAG, "readAll: Finished looping through the results");
        }

        // Cleanup
        if (!cursor.isClosed()) cursor.close();

        return notices;
    }

    @Override
    public Notice update(Notice notice) {
        Log.d(TAG, "update: Updating Notice with id: " + notice.getId());

        ContentValues cv = new ContentValues();
        cv.put(FIELD_TITLE,         notice.getTitle());
        cv.put(FIELD_IMPORTANCE,    notice.getImportance());
        cv.put(FIELD_THUMBNAIL,     notice.getThumbnailUrl());
        cv.put(FIELD_ICON,          notice.getIconUrl());
        cv.put(FIELD_DESCRIPTION,   notice.getDescription());
        cv.put(FIELD_DATE,          notice.getDateAndTime().getTime());

        Log.d(TAG, "update: Fires off the UPDATE statement");

        this.database.update(TABLE_NAME, cv, "id=" + notice.getId(), null);

        return notice;
    }

    @Override
    public boolean delete(int id) {
        Log.d(TAG, "delete: Deletes the notice with the id: " + id);
        int result = this.database.delete(TABLE_NAME, FIELD_ID + " = ?", new String[] {""+id});
        Log.d(TAG, "delete: Affected rows: " + result);
        return result >= 1;
    }

    private Notice assembleNotice(Cursor cursor) {
        Log.d(TAG, "assembleNotice: Initiating assembly");
        int i = -1;

        Log.d(TAG, "assembleNotice: Extracting the data from the cursor");
        final int id = cursor.getInt(++i);
        final String title = cursor.getString(++i);
        final String importance = cursor.getString(++i);
        final String thumbnail = cursor.getString(++i);
        final String icon = cursor.getString(++i);
        final String description = cursor.getString(++i);
        final long time = cursor.getLong(++i);

        Log.d(TAG, "assembleNotice: Creating a new Notice based on the data");
        final Notice notice = new Notice(title, new Date(time), description, importance);
        notice.setId(id);
        notice.setImportance(importance);
        notice.setThumbnailUrl(thumbnail);
        notice.setIconUrl(icon);
        notice.setDescription(description);

        Log.d(TAG, "assembleNotice() returned: " + notice);
        return notice;
    }

    private void createTable(SQLiteDatabase db) {
        Log.d(TAG, "createTable: Creating a new table");
        final String s = "CREATE TABLE " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIELD_TITLE + " TEXT, " +
                FIELD_IMPORTANCE + " INTEGER DEFAULT 0, " +
                FIELD_THUMBNAIL + " TEXT, " +
                FIELD_ICON + " TEXT, " +
                FIELD_DESCRIPTION + " TEXT, " +
                FIELD_DATE + " NUMERIC " +
                ")";
        db.execSQL(s);
    }

    private void dropTable(SQLiteDatabase db) {
        Log.d(TAG, "dropTable: Dropping table");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
            Log.d(TAG, "OpenHelper: OpenHelper created");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "onCreate: Creating the table");
            createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "onUpgrade: Dropping table");
            dropTable(db);
        }
    }

    /**
     * Seeds the SQLite DB
     */
    public void seed() {
        dropTable(this.database);
        createTable(this.database);

        int i = 0;
        Notice notice = new Notice("1", new Date(), "test", "Important");
        notice.setTitle("Title1");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        create(notice);

        notice = new Notice("2", new Date(), "test", "Important");
        notice.setTitle("Title2");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        create(notice);

        notice = new Notice("3", new Date(), "test", "Important");
        notice.setTitle("Title3");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        create(notice);

    }
}
