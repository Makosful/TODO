package com.github.makosful.todo.gui.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.R;
import com.github.makosful.todo.be.Todo;
import com.github.makosful.todo.gui.model.MainModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.model = new MainModel(this);
    }

    /**
     * This class represents a single TODO item in the RecyclerView and holds the related
     * information
     */
    private class TodoViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TodoViewHolder";

        private TextView tv_title;
        private ConstraintLayout parent;

        /**
         * Package-private constructor.
         * Creates a new RecyclerView item based on the View passed in
         * @param itemView An inflated View of the Layout that should be used to display this item
         */
        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "Constructor: Super has been called");

            this.tv_title = itemView.findViewById(R.id.tv_adapter_todo_title);
            Log.d(TAG, "TodoViewHolder: Title has been found");
            this.parent = itemView.findViewById(R.id.adapter_item_parent);
            Log.d(TAG, "TodoViewHolder: Parent View has been found");
        }
    }
}
