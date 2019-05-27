package com.github.makosful.todo.bll.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.R;
import com.github.makosful.todo.gui.NotificationReceiver;

public class NotificationHelper extends ContextWrapper {

    /**
     * The idea of this class is to upkeep D-R-Y and only do this once.
     * The channels are meant for user friendly design, so that we can defer
     * DEFAULT notifications from IMPORTANT notifications, allowing user to pick which to mute
     */
    public static final String CHANNEL_1_ID = "Main notice channel";
    public static final String CHANNEL_2_ID = "Important notice channel";
    private NotificationManager noticeManager;
    private Bitmap bmp;
    private Context ctx;

    public NotificationHelper(Context base) {
        super(base);
        ctx = base;
        createNotificationChannels();
    }

    public NotificationManager getManager() {
        if (noticeManager == null) {
            noticeManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return noticeManager;
    }

    private void createNotificationChannels() {
        // Check if we are on Oreo (26) SDK or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*
             * Below are the two channels mentioned initially where we distribute notifications on.
             * Channel 1 will be the channel for DEFAULT notifications, where as
             * Channel 2 will be the channel for IMPORTANT notifications, allowing the user to mute default ones
             * and keep their alarms on their important notifications. This also allows the user to simply mute both channels.
             * This means that important notifications can, if permitted go off during normally restricted states.
             */
            createDefaultChannel();
            createImportantChannel();
        }
    }

    private void createImportantChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CHANNEL 2 | IMPORTANT
            NotificationChannel chan2 = new NotificationChannel(CHANNEL_2_ID, "Important Notice", NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(chan2);
        }
    }

    public NotificationCompat.Builder getImportantNotification(String title, String description, int id, String imgPath) {
        Intent i = new Intent(this, NotificationReceiver.class);
        PendingIntent pI = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), i, 0);
        i.putExtra(Common.EXTRA_DATA_NOTICE_DETAILS, id);

        if (imgPath != null) {
            bmp = BitmapFactory.decodeFile(imgPath);
        }

        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notifications_important) // Icon that is displayed
                .setContentTitle(title) // TITLE text
                .setContentText(description) // BODY text
                .setLargeIcon(bmp) // adds the "large" icon, when we expand the notification
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH) // priority to help android decide the importance
                .setCategory(NotificationCompat.CATEGORY_ALARM) // category to help Android decide the type of notice
                .setOnlyAlertOnce(false) // disables the app from not popping up every time.
                .setContentIntent(pI)
                .setLights(Color.RED, 500, 250)
                .setVibrate(new long[] {0, 500, 250, 500}) // sets the vibration pattern
                .setAutoCancel(true); //Allows to dismiss notification by tapping
    }

    private void createDefaultChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CHANNEL 1 | DEFAULT
            NotificationChannel chan1 = new NotificationChannel(CHANNEL_1_ID, getString(R.string.defaultNotice), NotificationManager.IMPORTANCE_DEFAULT);
            getManager().createNotificationChannel(chan1);
        }
    }

    public NotificationCompat.Builder getDefaultNotification(String title, String description, int id) {
        Intent i = new Intent(this, NotificationReceiver.class);
        PendingIntent pI = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), i, 0);
        i.putExtra(Common.EXTRA_DATA_NOTICE_DETAILS, id);
        //TODO add .setContentIntent
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setContentTitle(title) // TITLE text
                .setContentText(description) // BODY text
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(new long[] {0, 500, 250, 500}) // sets the vibration pattern
                .setContentIntent(pI)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // priority to help android decide the importance
                .setSmallIcon(R.drawable.ic_notifications_default); // sets the icon of the notification

    }
}