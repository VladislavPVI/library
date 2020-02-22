package com.haulmont.testtask.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public interface Dao<T,Long> {

    T get(Long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}