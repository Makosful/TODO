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

        this.todoList = this.logic.getTodoStorage().readAll();
    }

    /**
     * Gets the cached list of Todo items
     * @return Returns a List collection of all Todo items
     */
    public List<Todo> getTodoList() {
        return this.todoList;
    }

    public void seedStorage() {
        this.todoList.add(new Todo("First"));
        this.todoList.add(new Todo("Second"));
        this.todoList.add(new Todo("Third"));
        this.todoList.add(new Todo("Fourth"));
        this.todoList.add(new Todo("Fifth"));
        this.todoList.add(new Todo("Sixth"));
        this.todoList.add(new Todo("Seventh"));
        this.todoList.add(new Todo("Eighth"));
        this.todoList.add(new Todo("Ninth"));
    }
}
