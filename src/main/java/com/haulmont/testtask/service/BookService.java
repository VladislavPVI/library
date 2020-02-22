package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.BookDao;
import com.haulmont.testtask.dao.Dao;
import com.haulmont.testtask.domain.Book;

import java.util.List;

public class BookService {
    private static BookDao bookDao;

    public BookService() {
        bookDao = new BookDao();
    }

    public Book get(Long id) {
        bookDao.openCurrentSession();
        Book book = bookDao.get(id);
        bookDao.closeCurrentSession();
        return null;
    }

    public List<Book> getAll() {
        bookDao.openCurrentSession();
        List<Book> books = bookDao.getAll();
        bookDao.closeCurrentSession();
        return books;
    }

    public void save(Book book) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.save(book);
        bookDao.closeCurrentSessionwithTransaction();

    }

    public void update(Book book) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.update(book);
        bookDao.closeCurrentSessionwithTransaction();

    }

    public void delete(Book book) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.delete(book);
        bookDao.closeCurrentSessionwithTransaction();

    }
}
