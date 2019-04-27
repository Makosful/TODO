package com.github.makosful.todo.gui.controller;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.R;
import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.gui.model.MainModel;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private MainModel model;

    private TextView tv_title;
    private ImageView iv_image;
    private TextView tv_description;

    private Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        this.model = new MainModel(this);

        this.tv_title = findViewById(R.id.tv_todo_detail_title);
        this.tv_description = findViewById(R.id.tv_detail_description);
        this.iv_image = findViewById(R.id.iv_todo_detail_image);

        int id = Objects.requireNonNull(getIntent().getExtras()).getInt(Common.EXTRA_DATA_TODO_ID);

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
}
