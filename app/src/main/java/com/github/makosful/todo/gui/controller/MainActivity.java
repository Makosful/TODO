package com.github.makosful.todo.gui.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.makosful.todo.R;
import com.github.makosful.todo.gui.model.MainModel;

/**
 * Launcher Activity.
 * This Activity will serve as a launcher and perform startup-only actions
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MainModel(this).seedStorage();

        startActivity(new Intent(this, TodoListActivity.class));
    }
}
