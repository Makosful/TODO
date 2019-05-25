package com.github.makosful.todo.gui.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.R;
import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.gui.NotificationReceiver;
import com.github.makosful.todo.gui.model.MainModel;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private MainModel model;

    private TextView tv_title;
    private ImageView iv_image;
    private TextView tv_description;
    private Button btnDelete;
    private int alarmID;
    private Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        this.model = new MainModel(this);

        this.tv_title = findViewById(R.id.tv_todo_detail_title);
        this.tv_description = findViewById(R.id.tv_detail_description);
        this.iv_image = findViewById(R.id.iv_todo_detail_image);

        this.btnDelete = findViewById(R.id.btn_todo_detail_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        int id = Objects.requireNonNull(getIntent().getExtras()).getInt(Common.EXTRA_DATA_TODO_ID);
        this.alarmID = id;
        //  gets the Notice entity from model
        Log.d(TAG, "Getting the selected Notice Item from the model");
        notice = model.getNotice(id);
        updateViews();
    }

    private void updateViews() {
        String title = this.notice.getTitle();
        tv_title.setText(title);

        String notes = this.notice.getDateAndTime().toString();
        tv_description.setText(notes);

        String url = this.notice.getThumbnailUrl();
        if (url == null || url.isEmpty()) {
            iv_image.setVisibility(View.INVISIBLE);
        } else {
            iv_image.setVisibility(View.VISIBLE);
            iv_image.setImageURI(Uri.parse(url));
        }
    }

    private void delete(){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);

        model.delete(alarmID);

        // Creates a new instance of the MainActivity to repopulate listview as a quick easy solution
        Intent x = new Intent(this, MainActivity.class);
        startActivity(x);
    }
}
