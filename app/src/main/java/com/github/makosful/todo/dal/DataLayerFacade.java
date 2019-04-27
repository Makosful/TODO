package com.github.makosful.todo.dal;

import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.dal.storage.IStorage;
import com.github.makosful.todo.dal.storage.TodoInMemory;

/**
 * This class serve as the Data Access Facade and will delegate data calls to their respective
 * implementations within the DAL package.
 */
public class DataLayerFacade implements IDAL {
    private IStorage<Notice> todoStorage;

    public DataLayerFacade() {
        this.todoStorage = new TodoInMemory();
    }

    @Override
    public IStorage<Notice> getTodoStorage() {
        return this.todoStorage;
    }
}
