package com.github.makosful.todo.bll.notifications;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import static android.content.ContentResolver.*;

public class NotificationContract {

    // The "Content authority" is a name for the entire content provider
    public static final String CONTENT_AUTH = "com.github.makosful.todo";

    // Use CONTENT_AUTHORITY to create the base of all URI's
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTH);

    public static final String PATH = "notification-path";

    public static final class NotificationEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH);

        public static final String CONTENT_LIST_TYPE = CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTH + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE = CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTH + "/" + PATH;

        public static final String TABLE_NAME = "notifications";

        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String IMPORTANCE = "importance";
        public static final String DESCRIPTION = "description";
        public static final String ACTIVE = "active";
    }

    public static String getColumnString(Cursor c, String colName) {
        return c.getString(c.getColumnIndex(colName));
    }
}
