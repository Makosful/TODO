package com.github.makosful.todo.bll;

import com.github.makosful.todo.be.Todo;
import com.github.makosful.todo.bll.storage.IStorage;

/**
 * Represents a promise of methods to handle logical functionality beyond remapping existing data,
 * such as validating data before sending it through to the DataAccessLayer.
 * This interface specifically promises to delegate the method calls to specific implementations in
 * the BLL package.
 */
public interface IBLL {
}
