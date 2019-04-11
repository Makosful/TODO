package com.github.makosful.todo.gui.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.makosful.todo.R;
import com.github.makosful.todo.gui.model.MainModel;

public class MainActivity extends AppCompatActivity {

    private MainModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.model = new MainModel(this);
    }
}
