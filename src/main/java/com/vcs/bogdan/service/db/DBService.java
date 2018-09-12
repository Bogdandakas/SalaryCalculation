package com.vcs.bogdan.service.db;

import java.util.List;

interface DBService<T> {

    T get(String id);

    void add(T obj);

    List<T> getAll();

    void remove(String id);
}
