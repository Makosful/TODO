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
import com.github.makosful.todo.be.Todo;
import com.github.makosful.todo.gui.model.MainModel;

public class TodoDetailActivity extends AppCompatActivity {
    private static final String TAG = "TodoDetailActivity";

    private MainModel model;

    private TextView tv_title;
    private ImageView iv_image;
    private TextView tv_description;

    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        this.model = new MainModel(this);

        this.tv_title = findViewById(R.id.tv_todo_detail_title);
        this.tv_description = findViewById(R.id.tv_detail_description);
        this.iv_image = findViewById(R.id.iv_todo_detail_image);

        int id = getIntent().getExtras().getInt(Common.EXTRA_DATA_TODO_ID);

        //  gets the Todo entity from model
        Log.d(TAG, "Getting the selected Todo Item from the model");

        todo = model.getTodo(id);
        updateViews();
    }

    private void updateViews() {
        String title = this.todo.getTitle();
        tv_title.setText(title);

        String notes = this.todo.getDateAndTime().toString();
        tv_description.setText(notes);

        String url = this.todo.getThumbnailUrl();
        if (url == null || url.isEmpty()) {
            iv_image.setVisibility(View.INVISIBLE);
        } else {
            iv_image.setVisibility(View.VISIBLE);
            iv_image.setImageURI(Uri.parse(url));
        }
    }
}
