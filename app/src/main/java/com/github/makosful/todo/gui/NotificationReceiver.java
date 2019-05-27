package com.github.makosful.todo.gui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.bll.notifications.NotificationHelper;
import com.github.makosful.todo.gui.model.MainModel;

import java.util.Objects;

public class NotificationReceiver extends BroadcastReceiver {
    /* TODO Finalize the notificationID below from autoIncrement in database. */
    private int i = 1;
    /***
     * This is what happens when we decide to press the activity, will be changed to display
     * DetailActivity for this specific task (if we make it that far)
     * @param context .
     * @param intent .
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        //Former way to try to get notices / Ended up in Nullpointers all the time.
        //Notice n = (Notice) Objects.requireNonNull(intent.getExtras()).get(Common.EXTRA_DATA_NOTIFICATION_NOTICE);
        //boolean isImportant = true;

        /**
         * A more direct way of getting the notification.
         */
        int id = Objects.requireNonNull(intent.getExtras())
                .getInt(Common.EXTRA_DATA_NOTIFICATION_NOTICE); //Changed to just get the Id
        MainModel model = new MainModel(context); //Creates new MainModel
        Notice n = model.getNotice(id); //Gets the notification from the DB

        NotificationHelper notificationHelper = new NotificationHelper(context);
        // Check if notifications is marked as IMPORTANT or not
        if(n.getImportance().equals("Important")) {
            NotificationCompat.Builder nb = notificationHelper.getImportantNotification(n.getTitle(), n.getDescription(), n.getId(), n.getThumbnailUrl());
            notificationHelper.getManager().notify(n.getId(), nb.build());
        } else if (n.getImportance().equals("Default")) {
            NotificationCompat.Builder nb = notificationHelper.getDefaultNotification(n.getTitle(), n.getDescription(), n.getId());
            notificationHelper.getManager().notify(n.getId(), nb.build());
        }
    }
}
