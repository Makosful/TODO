package com.github.makosful.todo.gui.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.makosful.todo.Common;
import com.github.makosful.todo.R;
import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.gui.model.MainModel;
import com.github.makosful.todo.gui.model.NoticeAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    NoticeAdapter adapter;
    List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Creates a new instance of the MainModel");

        MainModel model = new MainModel(this);

        Log.d(TAG, "onCreate: Getting the RecyclerView reference from the Layout file");
        // Views
        RecyclerView recyclerView = this.findViewById(R.id.rv_todo_list_list);

        noticeList = model.getNoticeList();

        Log.d(TAG, "onCreate: Creating a new TodoAdapter for the RecyclerView");
        // final TodoAdapter adapter = new TodoAdapter(this, model.getNoticeList());
        adapter = new NoticeAdapter(this, noticeList);
        recyclerView.setAdapter(adapter);

        // RecyclerView.Fixed Fixed allows for performance optimization.
        // If the View's visual size will never change, this can be set to true.
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void openAddActivity(View view) {
        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);
    }

    /**
     * This is the adapter class that's responsible for mapping the Notice Business Entity to the RecyclerView
     */
    private class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
        private static final String TAG = "TodoAdapter";

        private final Context context;
        private final List<Notice> noticeList;

        TodoAdapter(Context context, List<Notice> noticeList) {
            Log.d(TAG, "TodoAdapter() called with: context = [" + context + "], noticeList = [" + noticeList + "]");
            this.context = context;
            this.noticeList = noticeList;
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

            final Notice notice = this.noticeList.get(i);

            Log.d(TAG, "onBindViewHolder: Sets the contents of the current TODO item");
            todoViewHolder.tv_Title.setText(notice.getTitle());
            todoViewHolder.tv_Date_And_Time.setText(notice.getDateAndTime().toString());
            // TODO
            // todoViewHolder.tv_Importance.setText(notice.getImportance());
            todoViewHolder.iv_Thumbnail.setImageURI(Uri.parse(notice.getThumbnailUrl()));

            //Checks if iconUrl is null, if not sets the image.
            if (notice.getIconUrl() != null)
            {todoViewHolder.iv_Icon.setImageURI(Uri.parse(notice.getIconUrl())); }

            //If null it logs that there is a iconUrl set to null, which would result in a crash.
            else
                {Log.d(TAG, "IconURL is set to null which would result in a crash.");}

            todoViewHolder.outerParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDetailView(notice.getId());
                }
            });
        }

        /**
         * Gets the size of the adapter's list
         *
         * @return Returns an Integer representing the number of items known to the adapter.
         */
        @Override
        public int getItemCount() {
            final int size = this.noticeList.size();
            Log.d(TAG, "getItemCount: Current item count: " + size);
            return size;
        }

        /**
         * Opens the DetailActivity Activity.
         * The Notice item contained in the Activity is based on the ID passed in
         *
         * @param id The ID of the Notice item to fill the Activity with
         */
        private void openDetailView(int id) {
            Log.d(TAG, "openDetailView() called with: id = [" + id + "]");

            Log.d(TAG, "openDetailView: Creating new Intent for the DetailActivity class");
            Intent i = new Intent(this.context, DetailActivity.class);

            Log.d(TAG, "openDetailView: Added the ID to the Intent's extra");
            i.putExtra(Common.EXTRA_DATA_TODO_ID, id);

            Log.d(TAG, "openDetailView: Starts the DetailActivity Activity");
            ((Activity) this.context).startActivityForResult(i, Common.ACTIVITY_REQUEST_CODE_TODO_DETAIL);
        }
    }

    /**
     * This class represents a single to do item in the RecyclerView and holds the related information
     */
    private class TodoViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TodoViewHolder";

        private TextView tv_Title, tv_Date_And_Time, tv_Importance;
        private ImageView iv_Thumbnail, iv_Icon;
        private RelativeLayout outerParent;
        private LinearLayout innerParent;

        /**
         * Package-private constructor.
         * Creates a new RecyclerView item based on the View passed in
         *
         * @param itemView An inflated View of the Layout that should be used to display this item
         */
        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "Constructor: Super has been called");

            this.tv_Title = itemView.findViewById(R.id.notice_title);
            this.tv_Date_And_Time = itemView.findViewById(R.id.notice_date_time);
            this.tv_Importance = itemView.findViewById(R.id.notice_importance);
            this.iv_Thumbnail = itemView.findViewById(R.id.notice_thumbnail);
            this.iv_Icon = itemView.findViewById(R.id.notice_icon);
            this.outerParent = itemView.findViewById(R.id.outer_parent);
            this.innerParent = itemView.findViewById(R.id.inner_parent);
            Log.d(TAG, "TodoViewHolder: Parent View has been found");
        }
    }
}
