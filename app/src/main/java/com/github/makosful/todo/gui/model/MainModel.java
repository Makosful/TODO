package com.github.makosful.todo.gui.model;

import android.content.Context;

import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.bll.BusinessLayerFacade;
import com.github.makosful.todo.bll.IBLL;

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

    public boolean delete(int id) {
        return this.logic.getTodoStorage().delete(id);
    }

    public Notice addNotice(Notice notice) {
        return this.logic.getTodoStorage().create(notice);
    }
}
