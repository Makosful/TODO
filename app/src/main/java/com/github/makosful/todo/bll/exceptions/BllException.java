package com.github.makosful.todo.bll.exceptions;

public class BllException extends Exception {
    public BllException(String message) {
        super(message);
    }

    public BllException(String message, Throwable cause) {
        super(message, cause);
    }

    public BllException(Throwable cause) {
        super(cause);
    }
}
