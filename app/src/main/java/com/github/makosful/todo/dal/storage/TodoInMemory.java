package com.github.makosful.todo.dal.storage;

import com.github.makosful.todo.be.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a Mock class meant to simulate a Database
 */
public class TodoInMemory implements IStorage<Notice> {
    private static List<Notice> noticeList;

    public TodoInMemory() {
        if (noticeList == null)
            noticeList = new ArrayList<>();
    }

    @Override
    public boolean create(Notice item) {
        return noticeList.add(item);
    }

    @Override
    public Notice read(int id) {
        for (Notice notice : noticeList) {
            if (notice.getId() == id) {
                return notice;
            }
        }
        return null;
    }

    @Override
    public List<Notice> readAll() {
        return noticeList;
    }

    @Override
    public boolean update(Notice item) {
        int index = -1;
        for (int i = 0; i < noticeList.size(); i++) {
            if (noticeList.get(i).getId() == item.getId()) {
                index = i;
            }
        }

        if (index < 0) return false;
        else {
            noticeList.set(index, item);
            return true;
        }
    }

    @Override
    public boolean delete(int id) {
        return noticeList.remove(read(id));
    }
}
