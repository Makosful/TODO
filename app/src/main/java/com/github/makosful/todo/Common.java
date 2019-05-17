package com.github.makosful.todo;

public class Common {
    public static final String DATABASE_NAME = "notice_database";
    public static final int DATABASE_VERSION = 1;

    // Request codes for starting internal Activities.
    // Request codes here should stay in the 100 - 199 range.
    // The naming scheme of these fields are ACTIVITY_REQUEST_CODE_ followed by the given name of
    // the Activity
    public static final int ACTIVITY_REQUEST_CODE_TODO_CREATE = 100;
    public static final int ACTIVITY_REQUEST_CODE_TODO_LIST   = 101;
    public static final int ACTIVITY_REQUEST_CODE_TODO_DETAIL = 102;
    public static final int ACTIVITY_REQUEST_CODE_TODO_UPDATE = 103;

    // Request codes for starting external Activities (map, camera, etc)
    // Request codes here should stay in the 200 - 299 range.
    // The naming scheme of these fields are SERVICE_REQUEST_CODE_ followed by the name of the
    // Activity
    public static final int SERVICE_REQUEST_CODE_SAMLE = 0; // Sample, remove once a proper code has been defined

    // Request codes for permissions.
    // Request codes here should stay in the 300 - 399 range.
    // Field naming scheme is PERMISSION_REQUEST_CODE_ followed by the name of the permission(s)
    // If multiple permissions are requested using the same code, name them as a group instead.
    public static final int PERMISSION_REQUEST_CODE_SAMPLE = 0; // Sample, remove once a proper code has been defined

    // The names of the data passed into the Extra slot of Intents
    // Fields used for passing data into a StartActivityForResult should use the naming scheme
    // EXTRA_DATA_ followed by the name of the data being passed in.
    // Fields used to passing in result data should use the naming scheme EXTRA_RESULT_ followed by
    // the name of the data being passed in
    public static final String EXTRA_DATA_TODO    = "dataTodo";
    public static final String EXTRA_DATA_TODO_ID = "dataTodoId";
    public static final String EXTRA_DATA_NOTIFICATION_NOTICE = "notificationNotice";
    public static final String EXTRA_RESULT_TODO  = "resultTodo";
}
