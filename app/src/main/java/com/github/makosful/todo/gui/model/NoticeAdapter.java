package com.github.makosful.todo.gui.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.makosful.todo.R;
import com.github.makosful.todo.Common;
import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.gui.controller.DetailActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private static final String TAG = "Notice Adapter";

    private Context context;
    private List<Notice> noticeList;

    public NoticeAdapter(Context context, List<Notice> noticeList)
    {
        log("Creating adapter");
        this.context = context;
        this.noticeList = noticeList;
    }

    private static void log(String message){
        Log.d(TAG, message);
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        log("Creating ViewHolder");

        log("Inflating using custom layout");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_todo_item, viewGroup, false);

        log("Created ViewHolder");
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder viewHolder, int position)
    {
        log("Binding ViewHolder in position: " + position);

        log("Reading notice from position " + position);
        final Notice notice = noticeList.get(position);

        try {
            if (notice.getThumbnailUrl() != null) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(notice.getThumbnailUrl()));
                viewHolder.thumbnail.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            log(e.getMessage());
        }

        String title = notice.getTitle();
        if (title == null || title.isEmpty()) {
            log("Notice in position (" + position + ") appears to have no title");
            viewHolder.title.setText("");
        } else {
            log("Setting title");
            viewHolder.title.setText(title);
        }

        Date dateAndTime = notice.getDateAndTime();
        if (dateAndTime == null) {
            log("Notice in position (" + position + ") appears to have no date time");
            viewHolder.dateAndTime.setText("");
        } else {
            log("Setting description");
            // simple formatter to include both date and time with wanted format.
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy | HH:mm:ss z");
            viewHolder.dateAndTime.setText(formatter.format(dateAndTime));
        }

        boolean importance = notice.isImportance();
        if (!importance) {
            log("Notice in position (" + position + ") appears to have no importance");
            // TODO
            // viewHolder.importance.setText("");
        } else {
            log("Setting importance");
            // TODO
            // viewHolder.importance.setText(importance);
        }

        String description = notice.getDescription();
        if (description == null || description.isEmpty()) {
            log("Notice in position (" + position + ") appears to have no description");
            viewHolder.description.setText("");
        } else {
            log("Setting description");
            viewHolder.description.setText(description);
        }

        log("Setting the onClickListener for this item to open the notice detail view");
        viewHolder.outerParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailView(notice.getId());
            }
        });
    }

    /**
     * Opens a new Notification Activity. The contained notice is determined by the ID passed in
     * @param id The notifications ID
     */
    private void openDetailView(int id) {
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra(Common.EXTRA_DATA_TODO_ID, id);
        ((Activity)context).startActivityForResult(i, Common.ACTIVITY_REQUEST_CODE_TODO_DETAIL);
    }

    @Override
    public int getItemCount()
    {
        return noticeList.size();
    }


    class NoticeViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView thumbnail, icon;
        private TextView title, dateAndTime, importance, description;
        private RelativeLayout outerParent;
        private LinearLayout innerParent;

        NoticeViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //relative view, parent to thumbnail and linearview (inner parent)
            outerParent = itemView.findViewById(R.id.outer_parent);
            //linear view parent to everything EXCEPT thumbnail
            innerParent = itemView.findViewById(R.id.inner_parent);

            thumbnail = itemView.findViewById(R.id.notice_thumbnail);
            title = itemView.findViewById(R.id.notice_title);
            dateAndTime = itemView.findViewById(R.id.notice_date_time);
            importance = itemView.findViewById(R.id.notice_importance);
            description = itemView.findViewById(R.id.notice_description);
            icon = itemView.findViewById(R.id.notice_icon);

        }
    }
}
