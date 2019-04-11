package com.github.makosful.todo.dal.storage;

import com.github.makosful.todo.be.Todo;

import java.util.List;

/**
 * This class is a Mock class meant to simulate a Database
 */
public class TodoInMemory implements IStorage<Todo> {
    @Override
    public boolean create(Todo item) {
        return false;
    }

    @Override
    public Todo read(int id) {
        return null;
    }

    @Override
    public List<Todo> readAll() {
        return null;
    }

    @Override
    public boolean update(Todo item) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
