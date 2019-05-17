package com.github.makosful.todo.gui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.bll.notifications.NotificationHelper;

import java.util.Objects;

public class NotificationReceiver extends BroadcastReceiver {
    /* TODO Finalize the notificationID below from autoIncrement in database. */
    private int i = 1;
    /***
     * TODO Action when tapping notification (Toast atm)
     * This is what happens when we decide to press the activity, will be changed to display
     * DetailActivity for this specific task (if we make it that far)
     * @param context .
     * @param intent .
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Notice n = (Notice) Objects.requireNonNull(intent.getExtras()).get(Common.EXTRA_DATA_NOTIFICATION_NOTICE);
        boolean isImportant = true;
        NotificationHelper notificationHelper = new NotificationHelper(context);
        /* TODO Check if notifications is marked as IMPORTANT or not */
        if(!n.isImportance()) {
            NotificationCompat.Builder nb = notificationHelper.getImportantNotification();
            notificationHelper.getManager().notify(n.getId(), nb.build());
        } else {
            NotificationCompat.Builder nb = notificationHelper.getDefaultNotification();
            notificationHelper.getManager().notify(n.getId(), nb.build());
        }
    }
}
