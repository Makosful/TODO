package com.github.makosful.todo.bll.storage;

import java.io.Serializable;
import java.util.List;

/**
 * This interface promises to handle the logic behind the basic CRUD functionality of type T.
 * In order to save type T properly, the Business Entity has to implement the interface Serializable
 * @param <T> The Business Entity to handle. Must implement interface Serializable
 */
public interface IStorage<T extends Serializable> {
    /**
     * Creates a new instance of item T to the storage
     * @param item The new item to store
     * @return Returns a boolean representing if the item has been saved correctly
     */
    T create(T item);

    /**
     * Reads a single item from the storage based on the assigned ID
     * @param id The ID of the item to read
     * @return Returns the instance of the object with the same ID
     */
    T read(int id);

    /**
     * Reads all items from the storage
     * @return Returns a List of all items T
     */
    List<T> readAll();

    /**
     * Updates a single item T.
     * The ID of the given item is used to identify the item in storage that'll be updated.
     * All values, aside from the ID, contained in the given item will override the values in the
     * stored item, including null values.
     * @param item The item T to update
     * @return Returns a boolean representing whether the update was successful or not
     */
    boolean update(T item);

    /**
     * Deletes the object with the given ID from storage
     * @param id The ID of the item to delete
     * @return Returns a boolean representing whether the deletion was successful or not
     */
    boolean delete(int id);
}
