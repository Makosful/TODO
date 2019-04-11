package com.github.makosful.todo.dal.exceptions;

public class DalException extends Exception {
    public DalException(String message) {
        super(message);
    }

    public DalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DalException(Throwable cause) {
        super(cause);
    }
}
