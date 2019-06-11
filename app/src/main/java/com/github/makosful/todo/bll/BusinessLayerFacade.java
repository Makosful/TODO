package com.github.makosful.todo.bll;

import android.content.Context;

import com.github.makosful.todo.be.Notice;
import com.github.makosful.todo.bll.storage.IStorage;
import com.github.makosful.todo.bll.storage.TodoStorage;
import com.github.makosful.todo.dal.DataLayerFacade;
import com.github.makosful.todo.dal.IDAL;

/**'
 * This class serve as the Business Logic Facade and will delegate logic calls to their respective
 * implementations within the BLL class
 */
public class BusinessLayerFacade implements IBLL {
    private IDAL data;
    private IStorage<Notice> todoStorage;

    public BusinessLayerFacade(Context context) {
        // Creates an instance of the Data Layer Facade to pass around
        this.data = new DataLayerFacade(context);

        // Passes the DAL facade to the Storage handler
        this.todoStorage = new TodoStorage(data);
    }

    @Override
    public IStorage<Notice> getTodoStorage() {
        return this.todoStorage;
    }
}
