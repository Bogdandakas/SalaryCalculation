package com.vcs.bogdan.service.db;

import com.vcs.bogdan.beans.LogHandler;

import java.util.List;
import java.util.logging.Logger;

interface DBService<T> {

    T get(String id);

    void add(T obj);

    List<T> getAll();

    void remove(String id);
}
