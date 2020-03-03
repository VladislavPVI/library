package com.haulmont.testtask.dao;

import java.util.List;

public interface Dao<T, Long> {

    T get(Long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}