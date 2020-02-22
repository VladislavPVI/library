package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Book;
import com.haulmont.testtask.utils.HibernateUtil;
import java.util.List;

public class BookDao extends HibernateUtil implements Dao<Book, Long> {

    public BookDao() {
    }

    @Override
    public Book get(Long id) {
        return (Book) getCurrentSession().get(Book.class,id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> getAll() {
        return (List<Book>) getCurrentSession().createQuery("from Book").list();
    }

    @Override
    public void save(Book book) {
        getCurrentSession().save(book);

    }

    @Override
    public void update(Book book) {
        getCurrentSession().update(book);

    }

    @Override
    public void delete(Book book) {
        getCurrentSession().delete(book);

    }
}
