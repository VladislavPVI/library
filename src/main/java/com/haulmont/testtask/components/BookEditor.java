package com.haulmont.testtask.components;

import com.haulmont.testtask.domain.Author;
import com.haulmont.testtask.domain.Book;
import com.haulmont.testtask.domain.Genre;
import com.haulmont.testtask.domain.Publisher;
import com.haulmont.testtask.service.AuthorService;
import com.haulmont.testtask.service.BookService;
import com.haulmont.testtask.service.GenreService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;


class BookEditor extends Window {

    private VerticalLayout root = new VerticalLayout();
    private BookService bookService = new BookService();
    private AuthorService authorService = new AuthorService();
    private GenreService genreService = new GenreService();
    private Button save = new Button("OK", VaadinIcons.CHECK);
    private Button cancel = new Button("Cancel");
    private HorizontalLayout buttons = new HorizontalLayout(save,cancel);
    private Book book = new Book();
    private static final String str = "Input must be at least 1 character. Can't include: symbols, numbers, unusual capitalization, repeating characters or punctuation.";

    private TextField title = new TextField("Title");
    private ComboBox<Author> author = new ComboBox<>("Author");
    private ComboBox<Genre> genre = new ComboBox<>("Genre");
    private ComboBox<Publisher> publisher = new ComboBox<>("Publisher");
    private TextField year = new TextField("Year");


    private TextField city = new TextField("City");

    private Binder<Book> binder = new Binder<>(Book.class);



    public BookEditor(Book book) {
        super("Edit"); // Set window caption
        center();
        setClosable(false);

        this.book = book;
        author.setItems(authorService.getAll());

        genre.setItems(genreService.getAll());
        publisher.setItems(Publisher.values());

        binder.setBean(book);
        setUpBinder();


        save.addClickListener(this::buttonUpdate);
        cancel.addClickListener(e -> close());

        title.setSizeFull(); author.setSizeFull(); genre.setSizeFull(); year.setSizeFull(); publisher.setSizeFull(); city.setSizeFull(); buttons.setSizeFull();
        root.addComponents(title,author,genre,year,publisher,city,buttons);
        setContent(root);
        setWidth("318");
        setModal(true);
        setResizable(false);
        UI.getCurrent().addWindow(this);
    }

    public BookEditor() {

        super("Add new book"); // Set window caption
        center();
        setClosable(false);

        author.setItems(authorService.getAll());
        genre.setItems(genreService.getAll());
        publisher.setItems(Publisher.values());

        binder.setBean(book);
        setUpBinder();

        save.addClickListener(this::buttonSave);
        cancel.addClickListener(e -> close());

        title.setSizeFull(); author.setSizeFull(); genre.setSizeFull(); year.setSizeFull(); publisher.setSizeFull(); city.setSizeFull(); buttons.setSizeFull();
        root.addComponents(title,author,genre,year,publisher,city,buttons);
        setContent(root);
        setWidth("318");

        setModal(true);
        setResizable(false);
        UI.getCurrent().addWindow(this);
    }

    private static boolean test(String s) {
        return s.trim().matches("[А-Яа-я0-9-\\s]+") || s.trim().matches("[a-zA-Z0-9-\\s]+");
    }

    private void setUpBinder(){


        binder.forField(title).asRequired("Enter title!").withValidator(BookEditor::test,str).bind(Book::getTitle, Book::setTitle);
        binder.forField(author).asRequired("Choose an author!").bind(Book::getAuthor,Book::setAuthor);
        binder.forField(genre).asRequired("Choose a genre!").bind(Book::getGenre,Book::setGenre);
        binder.forField(publisher).asRequired("Choose a publisher!").bind(Book::getPublisher,Book::setPublisher);
        binder.forField(year).asRequired("Enter year!").withConverter(new StringToIntegerConverter("Invalid number"))
                .withValidator(validity -> validity <= 2020 && validity > 0, "Invalid year")
                .bind(Book::getYear,Book::setYear);
        binder.forField(city).asRequired("Enter city!").withValidator(BookEditor::test,str).bind(Book::getCity,Book::setCity);


       // binder.forField(patronymic).
        //        withValidator(AuthorEditor::test,str).
          //      bind(Author::getPatronymic, Author::setPatronymic);



    }

    private void buttonUpdate(Button.ClickEvent e){
        if(!binder.isValid()) {
            new Notification("System message",
                    "Check all fields",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            return;
        }
        bookService.update(book);
        new Notification("System message",
                "Book has been updated",
                Notification.Type.HUMANIZED_MESSAGE, true)
                .show(Page.getCurrent());
        close();

    }

    private void buttonSave(Button.ClickEvent e) {
        if (!binder.isValid()) {
            new Notification("System message",
                    "Check all fields",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            return;
        }
        bookService.save(book);
        new Notification("System message",
                "New book has been added",
                Notification.Type.HUMANIZED_MESSAGE, true)
                .show(Page.getCurrent());
        close();
    }
}