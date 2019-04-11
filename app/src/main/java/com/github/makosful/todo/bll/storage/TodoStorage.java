package com.github.makosful.todo.bll.storage;

import com.github.makosful.todo.be.Todo;
import com.github.makosful.todo.dal.IDAL;

import java.util.List;

/**
 * This class is responsible for handling any and all validation regarding the act of reading and
 * writing TODO items to and from storage
 */
public class TodoStorage implements IStorage<Todo> {
    /**
     * Implementation of the DataAccessLayer Facade
     */
    private final IDAL data;

    public TodoStorage(IDAL dal) {
        this.data = dal;
    }

    @Override
    public boolean create(Todo item) {
        return this.data.getTodoStorage().create(item);
    }

    @Override
    public Todo read(int id) {
        return this.data.getTodoStorage().read(id);
    }

    @Override
    public List<Todo> readAll() {
        return this.data.getTodoStorage().readAll();
    }

    @Override
    public boolean update(Todo item) {
        return this.data.getTodoStorage().update(item);
    }

    @Override
    public boolean delete(int id) {
        return this.data.getTodoStorage().delete(id);
    }
}