package com.github.makosful.todo.gui.controller;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.makosful.todo.R;

import static com.github.makosful.todo.bll.notifications.NotificationChannelInitiator.CHANNEL_1_ID;
import static com.github.makosful.todo.bll.notifications.NotificationChannelInitiator.CHANNEL_2_ID;

public class NotificationActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText et_title;
    private EditText et_message;
    private int noticeId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationManager = NotificationManagerCompat.from(this);
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);

    }

    public void sendDefaultNotice(View view) {
        Notification notice = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notifications_default)
                .setContentTitle(et_title.getText().toString())
                .setContentText(et_message.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();

        notificationManager.notify(noticeId++, notice);
    }

    public void sendImportantNotice(View view) {
        Notification notice = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notifications_important)
                .setContentTitle(et_title.getText().toString())
                .setContentText(et_message.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();

        notificationManager.notify(noticeId++, notice);
    }
}
