package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Genre;
import com.haulmont.testtask.utils.HibernateUtil;
import java.util.List;

public class GenreDao extends HibernateUtil implements Dao<Genre, Long> {

    @Override
    public Genre get(Long id) {
        return (Genre) getCurrentSession().get(Genre.class,id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Genre> getAll() {
        return (List<Genre>) getCurrentSession().createQuery("from Genre").list();
    }

    @Override
    public void save(Genre genre) {
        getCurrentSession().save(genre);

    }

    @Override
    public void update(Genre genre) {
        getCurrentSession().update(genre);

    }

    @Override
    public void delete(Genre genre) {
        getCurrentSession().delete(genre);

    }
}
