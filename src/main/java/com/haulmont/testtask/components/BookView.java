package com.haulmont.testtask.components;

import com.haulmont.testtask.domain.Author;
import com.haulmont.testtask.domain.Book;
import com.haulmont.testtask.domain.Genre;
import com.haulmont.testtask.domain.Publisher;
import com.haulmont.testtask.service.AuthorService;
import com.haulmont.testtask.service.BookService;
import com.haulmont.testtask.service.GenreService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class BookView extends Composite implements View {

    private VerticalLayout root;
    private BookService bookService = BookService.getInstance();
    private AuthorService authorService = AuthorService.getInstance();
    private GenreService genreService = GenreService.getInstance();
    private Grid<Book> grid = new Grid<>();
    private ListDataProvider<Book> dataProvider;
    private final Button addNewButton = new Button("New book", VaadinIcons.PLUS);


    public BookView() {
        setupLayout();
        addHeader();
        addTable();
    }

    private void addHeader() {
        Label header = new Label("Books");
        header.addStyleName(ValoTheme.LABEL_H2);
        addNewButton.addClickListener(e -> {
            BookEditor sub = new BookEditor();
            sub.addCloseListener(closeEvent -> grid.setItems(bookService.getAll()));

        });

        root.addComponent(header);
        root.addComponent(addNewButton);

    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        setCompositionRoot(root);
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private void addTable() {

        List<Book> bookList = bookService.getAll();
        dataProvider = new ListDataProvider<>(bookList);
        grid.setDataProvider(dataProvider);

        Grid.Column<Book, String> titleColumn = grid.addColumn(Book::getTitle).setCaption("Title");
        Grid.Column<Book, Author> authorColumn = grid.addColumn(Book::getAuthor).setCaption("Author");
        Grid.Column<Book, Genre> genreColumn = grid.addColumn(Book::getGenre).setCaption("Genre");
        Grid.Column<Book, Integer> yearColumn = grid.addColumn(Book::getYear).setCaption("Year");
        Grid.Column<Book, Publisher> publisherColumn = grid.addColumn(Book::getPublisher).setCaption("Publisher");
        Grid.Column<Book, String> cityColumn = grid.addColumn(Book::getCity).setCaption("City");

        grid.addColumn(person -> "Delete",
                new ButtonRenderer(this::clickDelete));
        grid.addColumn(person -> "Change",
                new ButtonRenderer(this::clickChange));


        HeaderRow filterRow = grid.appendHeaderRow();

        TextField titleFilter = new TextField();
        titleFilter.addValueChangeListener(event -> dataProvider.addFilter(s -> caseInsensitiveContains(s.getTitle(), titleFilter.getValue())));
        titleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(titleColumn).setComponent(titleFilter);
        titleFilter.setSizeFull();
        titleFilter.setPlaceholder("Filter by title");

        TextField cityFilter = new TextField();
        cityFilter.addValueChangeListener(event -> dataProvider.addFilter(s -> caseInsensitiveContains(s.getCity(), cityFilter.getValue())));
        cityFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(cityColumn).setComponent(cityFilter);
        cityFilter.setSizeFull();
        cityFilter.setPlaceholder("Filter by city");

        TextField yearFilter = new TextField();
        yearFilter.addValueChangeListener(event -> dataProvider.addFilter(s -> caseInsensitiveContains(String.valueOf(s.getYear()), yearFilter.getValue())));
        yearFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(yearColumn).setComponent(yearFilter);
        yearFilter.setSizeFull();
        yearFilter.setPlaceholder("Filter by year");


        ComboBox<Author> authorFilter = new ComboBox<>();
        authorFilter.setItems(authorService.getAll());
        authorFilter.addValueChangeListener(event -> dataProvider.addFilter(s -> {
            if (authorFilter.getValue() == null) return true;
            else return s.getAuthor().equals(authorFilter.getValue());
        }));
        filterRow.getCell(authorColumn).setComponent(authorFilter);
        authorFilter.setSizeFull();
        authorFilter.setPlaceholder("Filter by author");

        ComboBox<Genre> genreFilter = new ComboBox<>();
        genreFilter.setItems(genreService.getAll());
        genreFilter.addValueChangeListener(event -> dataProvider.addFilter(s -> {
            if (genreFilter.getValue() == null) return true;
            else return s.getGenre().equals(genreFilter.getValue());
        }));
        filterRow.getCell(genreColumn).setComponent(genreFilter);
        genreFilter.setSizeFull();
        genreFilter.setPlaceholder("Filter by genre");

        ComboBox<Publisher> publisherFilter = new ComboBox<>();
        publisherFilter.setItems(Publisher.values());
        publisherFilter.addValueChangeListener(event -> dataProvider.addFilter(s -> {
            if (publisherFilter.getValue() == null) return true;
            else return s.getPublisher().equals(publisherFilter.getValue());
        }));
        filterRow.getCell(publisherColumn).setComponent(publisherFilter);
        publisherFilter.setSizeFull();
        publisherFilter.setPlaceholder("Filter by publisher");

        grid.setSizeFull();
        grid.setHeightMode(HeightMode.UNDEFINED);
        root.addComponent(grid);

    }


    private void clickDelete(ClickableRenderer.RendererClickEvent clickEvent) {
        try {
            bookService.delete((Book) clickEvent.getItem());
            new Notification("System message",
                    "Book has been removed",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            grid.setItems(bookService.getAll());

        } catch (Exception e) {
            new Notification("ERROR",
                    "integrity constraint violation",
                    Notification.Type.ERROR_MESSAGE, true)
                    .show(Page.getCurrent());
        }
    }

    private void clickChange(ClickableRenderer.RendererClickEvent clickEvent) {
        BookEditor sub = new BookEditor((Book) clickEvent.getItem());
        sub.addCloseListener(closeEvent -> grid.setItems(bookService.getAll()));
    }
}


