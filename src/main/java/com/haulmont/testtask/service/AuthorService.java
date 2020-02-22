package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.AuthorDao;
import com.haulmont.testtask.domain.Author;

import java.util.List;

public class AuthorService {
    private static AuthorDao authorDao;

    public AuthorService() {
        authorDao = new AuthorDao();
    }

    public Author get(Long id) {
        authorDao.openCurrentSession();
        Author author = authorDao.get(id);
        authorDao.closeCurrentSession();
        return author;
    }

    public List<Author> getAll() {
        authorDao.openCurrentSession();
        List<Author> authors = authorDao.getAll();
        authorDao.closeCurrentSession();
        return authors;
    }

    public void save(Author author) {
        authorDao.openCurrentSessionwithTransaction();
        authorDao.save(author);
        authorDao.closeCurrentSessionwithTransaction();

    }


    public void update(Author author) {
        authorDao.openCurrentSessionwithTransaction();
        authorDao.update(author);
        authorDao.closeCurrentSessionwithTransaction();

    }


    public void delete(Author author) {
        authorDao.openCurrentSessionwithTransaction();
        authorDao.delete(author);
        authorDao.closeCurrentSessionwithTransaction();

    }
}
