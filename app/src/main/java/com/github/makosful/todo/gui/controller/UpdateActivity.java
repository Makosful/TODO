package com.github.makosful.todo.gui.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.makosful.todo.Common;
import com.github.makosful.todo.R;
import com.github.makosful.todo.be.Notice;

import java.util.Objects;

public class UpdateActivity extends AppCompatActivity implements TodoFragment.TodoFragmentCallback {
    private static final String TAG = "UpdateActivity";

    private Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_update);

        Log.d(TAG, "onCreate: Reads the Extra data passed into this Intent");
        Notice notice = (Notice) Objects.requireNonNull(getIntent().getExtras()).get(Common.EXTRA_DATA_TODO);
        TodoFragment frag = (TodoFragment) getSupportFragmentManager().findFragmentById(R.id.frag_todo_update_fragment);
        assert frag != null;
        Log.d(TAG, "onCreate: Sets the TODO value of the Fragment");
        frag.setNotice(notice);
    }

    @Override
    public void onBackPressed() {
        cancelActivity();
    }

    @Override
    public void updateNotice(Notice notice) {
        this.notice = notice;
    }

    /**
     * Cancels this Activity
     * @param view The View that calls this method
     */
    public void cancel(View view) {
        this.cancelActivity();
    }

    /**
     * Saves the result and closes the activity
     * @param view The View that calls this method
     */
    public void save(View view) {
        saveResults();
    }

    /**
     * Sets the results of this Activity and finishes it
     */
    private void saveResults() {
        Log.d(TAG, "saveResults() called");

        Log.d(TAG, "saveResults: Sets results");
        Intent i = new Intent();
        i.putExtra(Common.EXTRA_RESULT_TODO, this.notice);
        setResult(Activity.RESULT_OK, i);

        finish();
    }

    /**
     * Sets the results as canceled and finishes the the Activity
     */
    private void cancelActivity() {
        Log.d(TAG, "cancelActivity() called");
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
