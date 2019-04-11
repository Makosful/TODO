package com.github.makosful.todo.dal.storage;

import com.github.makosful.todo.be.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a Mock class meant to simulate a Database
 */
public class TodoInMemory implements IStorage<Todo> {
    private static List<Todo> todoList;

    public TodoInMemory() {
        if (todoList == null)
            todoList = new ArrayList<>();
    }

    @Override
    public boolean create(Todo item) {
        return todoList.add(item);
    }

    @Override
    public Todo read(int id) {
        for (Todo todo: todoList) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

    @Override
    public List<Todo> readAll() {
        return todoList;
    }

    @Override
    public boolean update(Todo item) {
        int index = -1;
        for (int i = 0; i < todoList.size(); i++) {
            if (todoList.get(i).getId() == item.getId()) {
                index = i;
            }
        }

        if (index < 0) return false;
        else {
            todoList.set(index, item);
            return true;
        }
    }

    @Override
    public boolean delete(int id) {
        return todoList.remove(read(id));
    }
}
