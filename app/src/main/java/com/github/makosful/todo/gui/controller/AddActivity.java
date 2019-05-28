package com.github.makosful.todo.gui.controller;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.R;
import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.gui.Custom.Camera;
import com.github.makosful.todo.gui.NotificationReceiver;
import com.github.makosful.todo.gui.model.MainModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.github.makosful.todo.bll.notifications.NotificationHelper.CHANNEL_2_ID;

public class AddActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddActivity";

    private NotificationManagerCompat notificationManager;
    private EditText etTitle, etDescription;
    private TextView tvPriority, tvDate, tvTime;
    private Calendar c;
    private Date mDate;
    private int noticeId = 1;
    private int mMinute, mHour, mDay, mMonth, mYear;
    private MainModel model;

    private Camera camera;
    private Uri imagePath;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);

        notificationManager = NotificationManagerCompat.from(this);
        model = new MainModel(this);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        tvTime = findViewById(R.id.tvTime);
        tvDate = findViewById(R.id.tvDate);
        tvPriority = findViewById(R.id.tvPriority);
        // Set default value
        tvPriority.setText(R.string.unimportant);
        Button btnAddActivity = findViewById(R.id.btnAddActivity);
        // Easter Egg
        btnAddActivity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // easter egg
                sendImportantNotice(v);
                Toast.makeText(AddActivity.this, "This is not the feature you're looking for.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Gets all necessary fields to set timer for the given time
        c = Calendar.getInstance();
        mMinute = c.get(Calendar.MINUTE);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);

        //fill fields by default with current time when starting activity.
        mDate = c.getTime();
        tvDate.setText(dateFormatter.format(mDate));
        tvTime.setText(timeFormatter.format(mDate));

        this.camera = new Camera(this);
    }

    /**
     * Handles the response for when Activities called by this Activity, using the
     * startActivityForResult method after they've called finish().
     *
     * @param requestCode The code used to launch the other Activity. Used to identify the
     *                    appropriate response
     * @param resultCode A code signaling whether the Activity was finished ot canceled. See
     *                   Activity.RESULT_CANCELED and Activity.RESULT_OK. Custom codes can also be
     *                   used
     * @param data The Intent containing the result data, if any.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) return;

        switch (requestCode) {
            case Common.SERVICE_REQUEST_CODE_CAMERA:
                handleCameraResponse();
                break;
            default:
                Toast.makeText(this, "Unknown request code", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * Handles the actions taken after the camera has taken the picture.
     * In case the Camera was canceled and no picture was taken, then the appropriate action should
     *      be taken in onActivityResult
     */
    private void handleCameraResponse() {
        // URI saved in this.imagePath;
        Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();
        Log.d(TAG, "handleCameraResponse: " + this.imagePath);
    }

    /**
     * Handles permission requests sent from this activity
     *
     * @param requestCode The code used to make the request. Used to identify the appropriate
     *                    response
     * @param permissions The array of permissions asked for in the request
     * @param grantResults A parallel array to the permissions array containing the response
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Common.PERMISSION_REQUEST_CODE_CAMERA:
                handleCameraPermissionResult(permissions, grantResults);
                break;
            default:
                Toast.makeText(this, "Unknown request code", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * Checks if any permission has been denied. If all have been granted, launches the Camera
     * @param permissions An array containing all the permissions asked for
     * @param grantResults An array containing the result for the request
     */
    private void handleCameraPermissionResult(String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                String msg = "Failed to get permission for [" +
                        permissions[i] +
                        "]";
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                return;
            }
        }

        this.imagePath = this.camera.launchCameraAndGetFileUri();
    }

    public void setTime(View view) {
        DialogFragment timeDialog = new com.github.makosful.todo.bll.notifications.TimePickerDialog();
        timeDialog.show(getSupportFragmentManager(), getString(R.string.timeDialog));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d("TimePicker", "LOGGING HOUR & MINUTE OF DAY: " + hourOfDay + " | " + minute);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        mDate = c.getTime();

        // To format the date to only include the time as well as timezone rather than full on date.
        tvTime.setText(timeFormatter.format(mDate));
    }

    public void setDate(View view) {
        DialogFragment dateDialog = new com.github.makosful.todo.bll.notifications.DatePickerDialog();
        dateDialog.show(getSupportFragmentManager(), getString(R.string.dateDialog));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d("DatePicker", "LOGGING DAY MONTH YEAR: " + year + " | " + month + " | " + dayOfMonth);
        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;
        c.set(year,month,dayOfMonth, 0,0);
        mDate = c.getTime();
        // To format the date to only include the date in the format we wish for. Format: 01 january 2019
        tvDate.setText(dateFormatter.format(mDate));
    }

    public void addPicture(View view) {
        // Asks for permission to use the camera and storage
        if (!this.camera.requestPermissions()) {
            // The boolean indicate whether a request was sent or not.
            // true means one or more permissions are missing, and a request has been sent
            //      and further actions should go through the onRequestPermissionsResult method
            // false means all permissions are granted and the app can proceed as normal.
            this.imagePath = this.camera.launchCameraAndGetFileUri();
        } // else, continue in onRequestPermissionsResult
    }

    public void addActivity(View view) {
        /*
        Check if user has disabled notifications for this APP, which is probably not intentional.
         */
        if (!notificationManager.areNotificationsEnabled()) {
            Toast.makeText(this, "Enable notifications", Toast.LENGTH_SHORT).show();
            openSettings();
            return;
        }

        /*
        Check if user has disabled this specific notifications CHANNEL which is probably not intentional
        REQUIRES API 26
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isNoticeChannelMuted()) {
            Toast.makeText(this, "Enable notifications", Toast.LENGTH_SHORT).show();
            openChannelSettings();
            return;
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        //new notice
        Notice n = new Notice(
                etTitle.getText().toString(),
                mDate,
                etDescription.getText().toString(),
                tvPriority.getText().toString());
        String s = this.imagePath.toString();
        // n.setThumbnailUrl(this.imagePath.toString());
        n.setThumbnailUrl(s);
        Notice notice = model.addNotice(n); // Stores the notice to get the id

        //Adds the Id as an "Extra" in the Intent.
        intent.putExtra(Common.EXTRA_DATA_NOTIFICATION_NOTICE, notice.getId());
        // unique request codes maybe?
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())) {
            Toast.makeText(this, "Sorry, we can't go back in time. Set time/date to a future date", Toast.LENGTH_LONG).show();
            return;
        }

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Reminder set for " + DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()), Toast.LENGTH_SHORT).show();

        // Creates a new instance of the MainActivity to repopulate listview as a quick easy solution
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }

    public void sendImportantNotice(View view) {
        /*
        When we tap the notification we open the details activity for this specific task (not to be confused with android Task)
        and we close the notification afterwards. In order to not launch activity upon activity we will make a stack builder
        so that we can simply add this fragment on top of our already existing task, rather than pile up activities.
        */
        Intent dI = new Intent(this, DetailActivity.class);
        TaskStackBuilder sB = TaskStackBuilder.create(this);
        sB.addNextIntentWithParentStack(dI);
        PendingIntent contentI = sB.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        /* This adds the bottom part of our notification, allowing us to perform certain actions when pressed. In this example
        all we do is make a toast, however this will most likely be used to "auto complete" the task without having to enter the
        GUI for the app itself, allowing for easy access of the user.
        A scenario where this is intended to directly solve is:
        If a user has done a task simply by remembering to do it, but have forgotten to mark it as finished in the app.
        */

        Intent bI = new Intent(this, NotificationReceiver.class);
        bI.putExtra("toastMsg", etDescription.getText().toString());
        PendingIntent aI = PendingIntent.getBroadcast(this, 0, bI, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bossrob);

        Notification notice = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notifications_important) // Icon that is displayed
                .setContentTitle("Hi there, I'm Bob Ross") // TITLE text
                .setContentText("I like to talk to trees and animals.") // BODY text
                .setLargeIcon(bmp) // adds the "large" icon, when we expand the notification
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH) // priority to help android decide the importance
                .setCategory(NotificationCompat.CATEGORY_REMINDER) // category to help Android decide the type of notice
                // .setContentIntent(contentI) //opens intent when pressed
                .setOnlyAlertOnce(false) // disables the app from not popping up every time.
                .setAutoCancel(true) //Allows to dismiss notification by tapping
                .build();

        notificationManager.notify(noticeId++, notice);
    }


    /**
     * Opens the settings tab for our application. The way we do this is feed android our package name
     * so it knows which app to open the settings for. We do this on both API >= 26 as well as above.
     */
    private void openSettings() {
        Toast.makeText(this, "Notifications have to be enabled!", Toast.LENGTH_LONG).show();
        // check if SDK >= Oreo (26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            startActivity(i);
        } else {
            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.setData(Uri.parse("package:" + getPackageName()));
            startActivity(i);
        }
    }

    /**
     * Opens the settings panel for the specific channel given.
     */
    @RequiresApi(26)
    private void openChannelSettings() {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, com.github.makosful.todo.bll.notifications.NotificationHelper.CHANNEL_2_ID);
        startActivity(i);
    }

    /**
     * Returns a boolean for if the given channel is muted or not.
     * @return .
     */
    @RequiresApi(26)
    private boolean isNoticeChannelMuted() {
        NotificationManager nM = getSystemService(NotificationManager.class);
        NotificationChannel nC = nM.getNotificationChannel(com.github.makosful.todo.bll.notifications.NotificationHelper.CHANNEL_2_ID);
        return nC != null && nC.getImportance() == NotificationManager.IMPORTANCE_NONE;
    }

    public void onSwitchRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            tvPriority.setText(R.string.important);
        } else {
            tvPriority.setText(R.string.unimportant);
        }
    }
}