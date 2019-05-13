package com.github.makosful.todo.gui.model;

import android.content.Context;
import android.util.Log;

import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.bll.BusinessLayerFacade;
import com.github.makosful.todo.bll.IBLL;
import java.util.Date;
import java.util.List;

public class MainModel {
    private static final String TAG = "MainModel";

    /**
     * Logic Layer Access
     */
    private IBLL logic;

    // Instance variables
    private List<Notice> noticeList;
    private Context context;

    public MainModel(Context context) {
        this.logic = new BusinessLayerFacade(context);
        // The context in which this model class lives
        this.context = context;
        this.noticeList = this.logic.getTodoStorage().readAll();
    }

    /**
     * Gets the cached list of Notice items
     * @return Returns a List collection of all Notice items
     */
    public List<Notice> getNoticeList() {
        return this.noticeList;
    }

    /**
     *
     * @param id The Id given to find the wanted Notice Item
     * @return Returns the item with the given Id from the Notice Items List
     */
    public Notice getNotice(int id) {
        return this.logic.getTodoStorage().read(id);
    }

    //TODO to be removed
    public void seedStorage() {
        Log.d(TAG, "Attempting to seed storage");
        //Clears all before it populates.
        noticeList.clear();

        int i = 0;
        Notice notice = new Notice("1", new Date());
        notice.setTitle("1");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("2", new Date());
        notice.setTitle("2");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("3", new Date());
        notice.setTitle("3");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("4", new Date());
        notice.setTitle("4");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("5", new Date());
        notice.setTitle("5");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("6", new Date());
        notice.setTitle("6");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("7",new Date());
        notice.setTitle("7");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("8", new Date());
        notice.setTitle("8");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);

        notice = new Notice("9", new Date());
        notice.setTitle("9");
        notice.setId(i++);
        notice.setThumbnailUrl("");
        notice.setIconUrl("");
        this.noticeList.add(notice);
        Log.d(TAG, "Seeded storage");
    }
}
