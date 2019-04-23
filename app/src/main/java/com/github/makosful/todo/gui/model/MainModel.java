package com.github.makosful.todo.gui.model;

import android.content.Context;

import com.github.makosful.todo.be.Todo;
import com.github.makosful.todo.bll.BusinessLayerFacade;
import com.github.makosful.todo.bll.IBLL;

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

    /**
     *
     * @param id The Id given to find the wanted Todo Item
     * @return Returns the item with the given Id from the Todo Items List
     */
    public Todo getTodo(int id) {return this.logic.getTodoStorage().read(id);}

   /*
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
     */


    public void seedStorage() {

        //Clears all before it populates.
        todoList.clear();

        int i = 0;
        Todo todo = new Todo("Title");
        todo.setId(i++);
        todo.setTitle("First");
        todo.setNotes(("Need more food"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Second");
        todo.setId(i++);
        todo.setTitle("Second");
        todo.setNotes(("Need more drinks"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Third");
        todo.setId(i++);
        todo.setTitle("Third");
        todo.setNotes(("Need more toilet paper"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Fourth");
        todo.setId(i++);
        todo.setTitle("Fourth");
        todo.setNotes(("Need more soap"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Fifth");
        todo.setId(i++);
        todo.setTitle("Fifth");
        todo.setNotes(("Need more coffee"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Sixth");
        todo.setId(i++);
        todo.setTitle("Sixth");
        todo.setNotes(("Need more extras"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Seventh");
        todo.setId(i++);
        todo.setTitle("Seventh");
        todo.setNotes(("Need more stuff"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Eighth");
        todo.setId(i++);
        todo.setTitle("Eighth");
        todo.setNotes(("Need more cat food"));
        todo.setImageUrl("");
        this.todoList.add(todo);

        todo = new Todo("Ninth");
        todo.setId(i++);
        todo.setTitle("Ninth");
        todo.setNotes(("Need more dog food"));
        todo.setImageUrl("");
        this.todoList.add(todo);
    }
}
