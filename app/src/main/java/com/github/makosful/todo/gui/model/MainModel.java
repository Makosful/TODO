package com.github.makosful.todo.gui.model;

import android.content.Context;
import com.github.makosful.todo.be.Todo;
import com.github.makosful.todo.bll.BusinessLayerFacade;
import com.github.makosful.todo.bll.IBLL;
import java.util.Date;
import java.util.List;

public class MainModel {
    /**
     * Logic Layer Access
     */
    private IBLL logic;

    // Instance variables
    private List<Todo> todoList;
    private Context context;

    public MainModel(Context context) {
        this.logic = new BusinessLayerFacade();
        // The context in which this model class lives
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

    /**
     *
     * @param id The Id given to find the wanted Todo Item
     * @return Returns the item with the given Id from the Todo Items List
     */
    public Todo getTodo(int id) {
        return this.logic.getTodoStorage().read(id);
    }

    public void seedStorage() {

        //Clears all before it populates.
        todoList.clear();

        int i = 0;
        Todo todo = new Todo("1", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("2", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("3", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("4", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("5", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("6", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("7",new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("8", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);

        todo = new Todo("9", new Date());
        todo.setId(i++);
        todo.setThumbnailUrl("");
        todo.setIconUrl("");
        this.todoList.add(todo);
    }
}
