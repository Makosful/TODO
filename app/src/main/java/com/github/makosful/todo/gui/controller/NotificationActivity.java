package com.github.makosful.todo.gui.controller;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.github.makosful.todo.R;
import com.github.makosful.todo.bll.notifications.NotificationReceiver;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import static com.github.makosful.todo.bll.notifications.NotificationHelper.CHANNEL_1_ID;
import static com.github.makosful.todo.bll.notifications.NotificationHelper.CHANNEL_2_ID;

public class NotificationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private NotificationManagerCompat notificationManager;
    private EditText etTitle;
    private EditText etDescription;
    private Calendar c;
    private Date mDate;
    private int noticeId = 1;
    private int mMinute, mHour, mDay, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);

        notificationManager = NotificationManagerCompat.from(this);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);

        // Gets all necessary fields to set timer for the given time
        c = Calendar.getInstance();
        mMinute = c.get(Calendar.MINUTE);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);
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
    }

    public void setDate(View view) {
        DialogFragment dateDialog = new com.github.makosful.todo.bll.notifications.DatePickerDialog();
        dateDialog.show(getSupportFragmentManager(), getString(R.string.timeDialog));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d("DatePicker", "LOGGING DAY MONTH YEAR: " + year + " | " + month + " | " + dayOfMonth);
        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;

        mDate = c.getTime();
    }

    public void addPicture(View view) {
        // TODO Add (optional) picture to the task.
        Toast.makeText(this, "Not added yet.", Toast.LENGTH_SHORT).show();
    }

    public void AddActivity(View view) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            Toast.makeText(this, "Sorry, we can't go back in time. Set time/date to a future date", Toast.LENGTH_LONG).show();
            return;
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Reminder set for " + DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()), Toast.LENGTH_SHORT).show();
    }

    public void sendImportantNotice(View view) {
        /*
        Check if user has disabled notifications for this APP, which is probably not intentional.
         */
        if (!notificationManager.areNotificationsEnabled()) {
            openSettings();
            return;
        }

        /*
        Check if user has disabled this specific notifications CHANNEL which is probably not intentional
        REQUIRES API 26
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isNoticeChannelMuted(CHANNEL_2_ID)) {
            openChannelSettings(CHANNEL_2_ID);
            return;
        }

        /*
        When we tap the notification we open the details activity for this specific task (not to be confused with android Task)
        and we close the notification afterwards. In order to not launch activity upon activity we will make a stack builder
        so that we can simply add this fragment on top of our already existing task, rather than pile up activities.*/
        Intent dI = new Intent(this, TodoDetailActivity.class);
        TaskStackBuilder sB = TaskStackBuilder.create(this);
        sB.addNextIntentWithParentStack(dI);
        PendingIntent contentI = sB.getPendingIntent(9, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
        This adds the bottom part of our notification, allowing us to perform certain actions when pressed. In this example
        all we do is make a toast, however this will most likely be used to "auto ocmplete" the task without having to enter the
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
                .setContentTitle(etTitle.getText().toString()) // TITLE text
                .setContentText(etDescription.getText().toString()) // BODY text
                .setLargeIcon(bmp) // adds the "large" icon, when we expand the notification
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH) // priority to help android decide the importance
                .setCategory(NotificationCompat.CATEGORY_REMINDER) // category to help Android decide the type of notice
                .setContentIntent(contentI) //opens intent when pressed
                .setOnlyAlertOnce(false) // disables the app from not popping up every time.
                .setAutoCancel(true) //Allows to dismiss notification by tapping
                .build();

        notificationManager.notify(noticeId++, notice);
    }

    public void sendDefaultNotice(View view) {
        Intent dI = new Intent(this, TodoDetailActivity.class);
        TaskStackBuilder sB = TaskStackBuilder.create(this);
        sB.addNextIntentWithParentStack(dI);
        PendingIntent contentI = sB.getPendingIntent(9, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent bI = new Intent(this, NotificationReceiver.class);
        bI.putExtra("toastMsg", etDescription.getText().toString());
        PendingIntent aI = PendingIntent.getBroadcast(this, 0, bI, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notice = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notifications_default)
                .setContentTitle(etTitle.getText().toString())
                .setContentText(etDescription.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(contentI)
                .addAction(R.mipmap.ic_launcher_round, getString(R.string.details), aI)
                .setOnlyAlertOnce(false)
                .setAutoCancel(true)
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

    @RequiresApi(26)
    private void openChannelSettings(String chanId) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, chanId);
        startActivity(i);
    }

    /**
     *
     * @param chanId .
     * @return .
     */
    @RequiresApi(26)
    private boolean isNoticeChannelMuted(String chanId) {
        NotificationManager nM = getSystemService(NotificationManager.class);
        NotificationChannel nC = nM.getNotificationChannel(chanId);
        return nC != null && nC.getImportance() == NotificationManager.IMPORTANCE_NONE;
    }
}