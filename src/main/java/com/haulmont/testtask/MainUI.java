package com.haulmont.testtask;

import com.haulmont.testtask.domain.Book;
import com.haulmont.testtask.service.BookService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private VerticalLayout root;
    private TextField textField = new TextField();
    private BookService bookService = BookService.getInstance();
    private Grid<Book> grid = new Grid<>(Book.class);
    private TextField textField1 = new TextField();

    @Override
    protected void init(VaadinRequest request) {
//        VerticalLayout layout = new VerticalLayout();
//        Grid<Book> grid = new Grid<>(Book.class);
//
//        MenuBar menuBar = new MenuBar();
//        MenuBar.MenuItem project = menuBar.addItem("Project");
//        MenuBar.MenuItem account = menuBar.addItem("Account");
//
//        final TextField name = new TextField();
//        name.setCaption("Type your name here:");
//        layout.addComponent(new Label("Main UI"));
//
//        Button button = new Button("Click Me");
//        button.addClickListener(e -> {
//            layout.addComponent(new Label("Thanks " + name.getValue()
//                    + ", it works!"));
//        });
//        Button button1 = new Button("ADD authors");
//        Button button2 = new Button("ADD genres");
//        Button button3 = new Button("ADD authors");
//
//        AuthorService authorService = new AuthorService();
//        BookService bookService = new BookService();
//        GenreService genreService = new GenreService();
//
//
//
//        button1.addClickListener(e ->
//        {
//            Author author = new Author("Булгаков","Михаил","Афанасьевич");
//            authorService.save(author);
//            Author author1 = new Author("Брэдбери","Рэй","Дуглас");
//            authorService.save(author1);
//            Author author2 = new Author("Достоевский", "Фёдор", "Михайлович");
//            authorService.save(author2);
//            Author author3 = new Author("Грибоедов","Александр","Сергеевич");
//            authorService.save(author3);
//            Author author4 = new Author("Грибоедов","Александр","Иванович");
//            authorService.save(author4);
//         });
//
//        button2.addClickListener(e ->
//        {
//            Genre genre = new Genre("роман");
//            genreService.save(genre);
//            Genre genre1 = new Genre("фантастика");
//            genreService.save(genre1);
//            Genre genre2 = new Genre("комедия");
//            genreService.save(genre2);
//
//    });
//        button3.addClickListener(e ->
//        {
//            Book book = new Book("451 градус по фаренгейту",authorService.get((long) 2),genreService.get((long) 2),"New York",1953,Publisher.OREILLY);
//            bookService.save(book);
//            Book book1 = new Book("Мастер и Маргарита",authorService.get((long) 1),genreService.get((long) 1),"Москва",1966,Publisher.МОСКВА);
//            bookService.save(book1);
//            Book book2 = new Book("Преступление и наказание",authorService.get((long) 3),genreService.get((long) 1),"Санкт-Петербург",1866,Publisher.ПИТЕР);
//            bookService.save(book2);
//            Book book3 = new Book("Горе от ума",authorService.get((long) 4),genreService.get((long) 3),"Москва",1825,Publisher.МОСКВА);
//            bookService.save(book3);
//
//        });
//        Button button4 = new Button("ADD grid");
//        button4.addClickListener(e ->
//        {
//          grid.setItems(bookService.getAll());
//          layout.addComponent(grid);
//        });
//
//        layout.addComponents(menuBar, name, button, button1, button2, button3, button4);
//        //layout.setSizeFull();
//        layout.setMargin(true);
//        setContent(layout);
        setupLayout();
        addHeader();
        addForm();
        addTable();
    }

    private void addTable() {
        HorizontalLayout form = new HorizontalLayout();
        form.setWidth("80%");

        grid.setItems(bookService.getAll());
        grid.setColumnOrder("id", "title", "author", "genre", "city", "year");
        grid.removeColumn("id");
        grid.setSizeFull();
        form.addComponent(grid);
        root.addComponent(form);
    }

    private void addForm() {
        HorizontalLayout form = new HorizontalLayout();
        form.setWidth("100%");
        textField.setPlaceholder("Filter by name...");
        textField.addValueChangeListener(this::onNameFilterTextChange);
        textField1.setPlaceholder("Filter by автор...");
        textField1.addValueChangeListener(this::onAuthorFilterTextChange);

        Button add = new Button("Add");
        form.addComponentsAndExpand(textField, textField1);
        form.addComponent(add);

        root.addComponent(form);


    }

    private void addHeader() {
        Label header = new Label("Hello!");
        header.addStyleName(ValoTheme.LABEL_H1);
        root.addComponent(header);

    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);

    }

    private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Book> dataProvider = (ListDataProvider<Book>) grid.getDataProvider();
        dataProvider.setFilter(Book::getTitle, s -> caseInsensitiveContains(s, event.getValue()));

    }

    private void onAuthorFilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Book> dataProvider = (ListDataProvider<Book>) grid.getDataProvider();
        dataProvider.setFilter(Book::getAuthor, s -> caseInsensitiveContains(s.toString(), event.getValue()));
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }


}