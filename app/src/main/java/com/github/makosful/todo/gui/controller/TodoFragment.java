package com.github.makosful.todo.gui.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.makosful.todo.be.Notice;

public class TodoFragment extends Fragment {
    private static final String TAG = "TodoFragment";

    private TodoFragmentCallback mListener;

    private Notice notice;

    /**
     * Required public constructor.
     */
    public TodoFragment() {
        Log.d(TAG, "TodoFragment() called");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Checks if this Fragment has any arguments");
        if (getArguments() != null) {
            Log.d(TAG, "onCreate: Reads the arguments");
            // Read argument
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");

        Log.d(TAG, "onCreateView: Calling super");
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Views

        Log.d(TAG, "onCreateView() returned: " + view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");

        Log.d(TAG, "onAttach: Calling super");
        super.onAttach(context);

        Log.d(TAG, "onAttach: Checking callback implementation");
        if (context instanceof TodoFragmentCallback) {
            mListener = (TodoFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement TodoFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach() called");
        super.onDetach();

        Log.d(TAG, "onDetach: Setting TodoFragmentCallback to null");
        this.mListener = null;
    }

    /**
     * Sets the local instance of the TODO entity
     * @param notice
     */
    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public interface TodoFragmentCallback {
        void updateNotice(Notice notice);
    }
}
