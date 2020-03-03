package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.Dao;
import com.haulmont.testtask.dao.GenreDao;
import com.haulmont.testtask.domain.Genre;

import java.util.List;

public class GenreService implements Dao<Genre, Long> {
    private static GenreDao genreDao;
    private static GenreService instance;

    public static synchronized GenreService getInstance() {
        if (instance == null) {
            instance = new GenreService();
        }
        return instance;
    }

    private GenreService() {
        genreDao = new GenreDao();
    }

    public Genre get(Long id) {
        genreDao.openCurrentSession();
        Genre genre = genreDao.get(id);
        genreDao.closeCurrentSession();
        return genre;
    }

    public List<Genre> getAll() {
        genreDao.openCurrentSession();
        List<Genre> genres = genreDao.getAll();
        genreDao.closeCurrentSession();
        return genres;
    }

    public void save(Genre genre) {
        genreDao.openCurrentSessionwithTransaction();
        genreDao.save(genre);
        genreDao.closeCurrentSessionwithTransaction();
    }

    public void update(Genre genre) {
        genreDao.openCurrentSessionwithTransaction();
        genreDao.update(genre);
        genreDao.closeCurrentSessionwithTransaction();
    }

    public void delete(Genre genre) {
        genreDao.openCurrentSessionwithTransaction();
        genreDao.delete(genre);
        genreDao.closeCurrentSessionwithTransaction();

    }
}
