package com.github.makosful.todo.dal;

/**
 * Represents a promise of methods related to reading and writing data to sources outside the APP
 * cache, such as the device's file storage, a database or an online storage. Communicating with
 * the device's sensors, such as the GPS, should happen through implementations of this interface.
 * This interface specifically is responsible for delegating method calls to specific implementations
 * in the DataAccessLayer package
 */
public interface IDAL {
}
