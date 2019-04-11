package com.github.makosful.todo.bll.storage;
import java.io.Serializable;
/**
 * This interface promises to handle the logic behind the basic CRUD functionality of type T.
 * In order to save type T properly, the Business Entity has to implement the interface Serializable
 * @param <T> The Business Entity to handle. Must implement interface Serializable
 */
public interface IStorage<T extends Serializable> {
}
