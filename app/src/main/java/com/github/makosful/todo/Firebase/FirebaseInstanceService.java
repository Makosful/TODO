package com.github.makosful.todo.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.github.makosful.todo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class FirebaseInstanceService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().isEmpty()) {
            showNotification(remoteMessage.getNotification().getTag(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } else {
            showNotification(remoteMessage.getData());
        }
    }

    private void showNotification(Map<String, String> data) {

    }

    private void showNotification(String tag, String title, String body) {
        NotificationManager noticeManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String notice_channel_id = "com.github.makosful.todo";

        // if SDK is > 28
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            NotificationChannel noticeChannel = new NotificationChannel(notice_channel_id, "Notification", NotificationManager.IMPORTANCE_HIGH);

            //description of our notification channel
            noticeChannel.setDescription("Todo channel");
            //enable light to show on phone and set light to RED
            noticeChannel.enableLights(true);
            noticeChannel.setLightColor(Color.RED);
            //vibration pattern, 0 sec delay, 1 sec delay, 0.5 sec delay, 1 sec delay
            noticeChannel.setVibrationPattern(new long[]{0,1000,500,1000});

            noticeManager.createNotificationChannel(noticeChannel);
        }

        NotificationCompat.Builder noticeBuilder = new NotificationCompat.Builder(this, notice_channel_id);

        noticeBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notice_icon).setContentTitle(title).setContentText(body).setContentInfo("Info");

        //provide random id and our notification itself.
        noticeManager.notify(new Random().nextInt(), noticeBuilder.build());
    }
}
