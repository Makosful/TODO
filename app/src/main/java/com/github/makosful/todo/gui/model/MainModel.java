package com.github.makosful.todo.gui.model;

import android.content.Context;

import com.github.makosful.todo.bll.BusinessLayerFacade;
import com.github.makosful.todo.bll.IBLL;

public class MainModel {
    // Logic Layer Access
    private IBLL logic;

    // The context in which this model class lives
    private Context context;

    public MainModel(Context context) {
        this.logic = new BusinessLayerFacade();

        this.context = context;
    }
}
