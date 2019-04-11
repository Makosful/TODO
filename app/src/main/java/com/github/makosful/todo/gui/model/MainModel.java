package com.github.makosful.todo.gui.model;

import android.content.Context;

import com.github.makosful.todo.be.Todo;
import com.github.makosful.todo.bll.BusinessLayerFacade;
import com.github.makosful.todo.bll.IBLL;

import java.util.ArrayList;
import java.util.List;

public class MainModel {
    /**
     * Logic Layer Access
     */
    private IBLL logic;

    /**
     * The context in which this model class lives
     */
    private Context context;

    // Instance variables
    private List<Todo> todoList;

    public MainModel(Context context) {
        this.logic = new BusinessLayerFacade();
        this.context = context;

        this.todoList = new ArrayList<>(); // Replace with a method call to the storage later
    }

    /**
     * Gets the cached list of Todo items
     * @return Returns a List collection of all Todo items
     */
    public List<Todo> getTodoList() {
        return this.todoList;
    }
}
