package com.github.makosful.todo.bll.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
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
        boolean isImportant = true;
        NotificationHelper notificationHelper = new NotificationHelper(context);
        /* TODO Check if notifications is marked as IMPORTANT or not */
        if(!isImportant) {
            NotificationCompat.Builder nb = notificationHelper.getImportantNotification();
            notificationHelper.getManager().notify(i++, nb.build());
        } else {
            NotificationCompat.Builder nb = notificationHelper.getDefaultNotification();
            notificationHelper.getManager().notify(i++, nb.build());
        }
    }
}
