package com.github.makosful.todo.be;

import java.io.Serializable;

public class Todo implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
