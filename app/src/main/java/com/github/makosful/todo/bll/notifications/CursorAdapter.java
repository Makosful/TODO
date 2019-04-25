package com.github.makosful.todo.bll.notifications;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.makosful.todo.R;

public class CursorAdapter extends android.widget.CursorAdapter {
    private TextView mTitle, mImportance, mDateAndTime, mDescription;
    private ImageView mIcon, mThumbnail;

    // Temporary shit, instead of an image from camera
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public CursorAdapter(View view, Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_todo_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mTitle = view.findViewById(R.id.notice_title);
        mDateAndTime = view.findViewById(R.id.notice_date_time);
        mDescription = view.findViewById(R.id.notice_description);
        mImportance = view.findViewById(R.id.notice_importance);
        mThumbnail = view.findViewById(R.id.notice_thumbnail);
        mIcon = view.findViewById(R.id.notice_icon);

        int titleColIndex = cursor.getColumnIndex(NotificationContract.NotificationEntry.TITLE);
        int dateColIndex = cursor.getColumnIndex(NotificationContract.NotificationEntry.DATE);
        int timeColIndex = cursor.getColumnIndex(NotificationContract.NotificationEntry.TIME);
        int descriptionColIndex = cursor.getColumnIndex(NotificationContract.NotificationEntry.DESCRIPTION);
        int importanceColIndex = cursor.getColumnIndex(NotificationContract.NotificationEntry.IMPORTANCE);
        int iconColIndex = cursor.getColumnIndex(NotificationContract.NotificationEntry.ACTIVE);

        String title = cursor.getString(titleColIndex);
        String description = cursor.getString(descriptionColIndex);
        String date = cursor.getString(dateColIndex);
        String time = cursor.getString(timeColIndex);
        String importance = cursor.getString(importanceColIndex);
        String icon = cursor.getString(iconColIndex);

        String dateAndTime = date + " | " + time;

        setTitle(title);
        setDescription(description);
        setDateAndTime(dateAndTime);
        setImportance(importance);
        // setIcon(icon);
    }

    private void setTitle(String title) {
        String letter = "A";
        mTitle.setText(title);

        if(title != null && !title.isEmpty()) {
            // if there's a title, replace the default A with the first letter of the titles string
            letter = title.substring(0, 1);
        }

        // Create a circular icon consisting of  a random background colour and first letter of title
        int color = mColorGenerator.getRandomColor();

        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color);
        mThumbnail.setImageDrawable(mDrawableBuilder);
    }

    private void setDescription(String description) {
        mDescription.setText(description);
    }

    private void setDateAndTime(String dateAndTime) {
        mDateAndTime.setText(dateAndTime);
    }

    private void setImportance(String importance) {
        mImportance.setText(importance);
    }

    private void setIcon(String icon) {
        // TODO Figure out what to do here? the icon will be a small indicator (to the the right in xml) that shows if notice is active or not.
        if (icon.equals("true")) {
           mIcon.setImageResource(R.drawable.ic_notifications_important);
        } else {
            mIcon.setImageResource(R.drawable.ic_notifications_default);
        }
    }
}
