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
     * This is the adapter class that's responsible for mapping the Todo Business Entity to the
     * RecyclerView
     */
    private class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
        private static final String TAG = "TodoAdapter";

        private final Context context;
        private final List<Todo> todoList;

        public TodoAdapter(Context context, List<Todo> todoList) {
            Log.d(TAG, "TodoAdapter() called with: context = [" + context + "], todoList = [" + todoList + "]");
            this.context = context;
            this.todoList = todoList;
        }

        @NonNull
        @Override
        public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Log.d(TAG, "onCreateViewHolder() called with: viewGroup = [" + viewGroup + "], i = [" + i + "]");

            Log.d(TAG, "onCreateViewHolder: Creating a LayoutInflater from the current Context");
            LayoutInflater inflater = LayoutInflater.from(this.context);

            Log.d(TAG, "onCreateViewHolder: Attaches the correct layout file to the adapter");
            View view = inflater.inflate(R.layout.adapter_todo_item, viewGroup, false);

            Log.d(TAG, "onCreateViewHolder: Creates a new TodoViewHolder and returns it");
            return new TodoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoViewHolder todoViewHolder, int i) {
            Log.d(TAG, "onBindViewHolder() called with: todoViewHolder = [" + todoViewHolder + "], i = [" + i + "]");

            final Todo todo = this.todoList.get(i);

            Log.d(TAG, "onBindViewHolder: Sets the title of the current TODO item");
            todoViewHolder.tv_title.setText(todo.getTitle());

            todoViewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDetailView(todo.getId());
                }
            });
        }

        /**
         * Gets the size of the adapter's list
         * @return Returns an Integer representing the number of items known to the adapter.
         */
        @Override
        public int getItemCount() {
            final int size = this.todoList.size();
            Log.d(TAG, "getItemCount: Current item count: " + size);
            return size;
        }

        /**
         * Opens the TodoDetail Activity.
         * The Todo item contained in the Activity is based on the ID passed in
         * @param id The ID of the Todo item to fill the Activity with
         */
        private void openDetailView(int id) {
            Log.d(TAG, "openDetailView() called with: id = [" + id + "]");

            Log.d(TAG, "openDetailView: Creating new Intent for the TodoDetail class");
            Intent i = new Intent(this.context, TodoDetail.class);

            Log.d(TAG, "openDetailView: Added the ID to the Intent's extra");
            i.putExtra(Common.EXTRA_DATA_TODO_ID, id);

            Log.d(TAG, "openDetailView: Starts the TodoDetail Activity");
            ((Activity)this.context).startActivityForResult(i, Common.ACTIVITY_REQUEST_CODE_TODO_DETAIL);
        }
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
