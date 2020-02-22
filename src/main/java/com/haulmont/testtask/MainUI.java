package com.haulmont.testtask;

import com.haulmont.testtask.domain.Author;
import com.haulmont.testtask.domain.Book;
import com.haulmont.testtask.domain.Genre;
import com.haulmont.testtask.domain.Publisher;
import com.haulmont.testtask.service.AuthorService;
import com.haulmont.testtask.service.BookService;
import com.haulmont.testtask.service.GenreService;
import com.haulmont.testtask.utils.HibernateUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {


    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        //layout.setSizeFull();
        //layout.setMargin(true);
        final TextField name = new TextField();
        name.setCaption("Type your name here:");
        layout.addComponent(new Label("Main UI"));

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
        });
        Button button1 = new Button("ADD authors");
        Button button2 = new Button("ADD genres");
        Button button3 = new Button("ADD authors");

        AuthorService authorService = new AuthorService();
        BookService bookService = new BookService();
        GenreService genreService = new GenreService();


        button1.addClickListener(e ->
        {
            Author author = new Author("Булгаков","Михаил","Афанасьевич");
            authorService.save(author);
            Author author1 = new Author("Брэдбери","Рэй","Дуглас");
            authorService.save(author1);
            Author author2 = new Author("Достоевский", "Фёдор", "Михайлович");
            authorService.save(author2);
            Author author3 = new Author("Грибоедов","Александр","Сергеевич");
            authorService.save(author3);
            Author author4 = new Author("Грибоедов","Александр","Иванович");
            authorService.save(author4);
         });

        button2.addClickListener(e ->
        {
            Genre genre = new Genre("роман");
            genreService.save(genre);
            Genre genre1 = new Genre("фантастика");
            genreService.save(genre1);
            Genre genre2 = new Genre("комедия");
            genreService.save(genre2);

    });
        button3.addClickListener(e ->
        {
            Book book = new Book("451 градус по фаренгейту",authorService.get((long) 2),genreService.get((long) 2),"New York",1953,Publisher.OREILLY);
            bookService.save(book);
            Book book1 = new Book("Мастер и Маргарита",authorService.get((long) 1),genreService.get((long) 1),"Москва",1966,Publisher.МОСКВА);
            bookService.save(book1);
            Book book2 = new Book("Преступление и наказание",authorService.get((long) 3),genreService.get((long) 1),"Санкт-Петербург",1866,Publisher.ПИТЕР);
            bookService.save(book2);
            Book book3 = new Book("Горе от ума",authorService.get((long) 4),genreService.get((long) 3),"Москва",1825,Publisher.МОСКВА);
            bookService.save(book3);
            HibernateUtil.shutdown();

        });

        layout.addComponents(name, button, button1, button2, button3);

        setContent(layout);
    }


}