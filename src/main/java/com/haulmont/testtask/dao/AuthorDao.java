package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Author;
import com.haulmont.testtask.utils.HibernateUtil;

import java.util.List;

public class AuthorDao extends HibernateUtil implements Dao<Author, Long> {

    @Override
    public Author get(Long id) {
        return getCurrentSession().get(Author.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Author> getAll() {
        return (List<Author>) getCurrentSession().createQuery("from Author").list();
    }

    @Override
    public void save(Author author) {
        getCurrentSession().save(author);

    }

    @Override
    public void update(Author author) {
        getCurrentSession().update(author);

    }

    @Override
    public void delete(Author author) {
        getCurrentSession().delete(author);

    }
}
