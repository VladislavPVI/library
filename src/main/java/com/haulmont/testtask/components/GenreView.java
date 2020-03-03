package com.haulmont.testtask.components;

import com.haulmont.testtask.domain.Book;
import com.haulmont.testtask.domain.Genre;
import com.haulmont.testtask.service.BookService;
import com.haulmont.testtask.service.GenreService;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class GenreView extends Composite implements View {

    private GenreEditor sub;
    private VerticalLayout root;
    private GenreService genreService = GenreService.getInstance();
    private Grid<Genre> grid = new Grid<>();
    private final Button addNewButton = new Button("New genre", VaadinIcons.PLUS);
    private final Button stat = new Button("Statistic", VaadinIcons.CALC);
    private BookService bookService = BookService.getInstance();
    private List<Book> books = bookService.getAll();
    private boolean statB = true;

    public GenreView() {

        setupLayout();
        addHeader();
        addTable();
    }

    private void addHeader() {
        Label header = new Label("Genres");
        header.addStyleName(ValoTheme.LABEL_H2);
        addNewButton.addClickListener(e -> {
            sub = new GenreEditor();
            sub.addCloseListener(closeEvent -> grid.setItems(genreService.getAll()));

        });
        stat.addClickListener(e -> {
            statB = !statB;
            grid.getColumn("numOfBooks").setHidden(statB);
            grid.getColumn("bar").setHidden(statB);

        });
        root.addComponent(header);
        HorizontalLayout horizontalLayout = new HorizontalLayout(addNewButton, stat);
        root.addComponent(horizontalLayout);

    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        setCompositionRoot(root);
    }


    private void addTable() {

        grid.setItems(genreService.getAll());
        grid.addColumn(Genre::toString)
                .setCaption("Name of genre");

        grid.addColumn(this::getCount).setCaption("Number of books").setId("numOfBooks").setHidden(statB);


        grid.addColumn(person -> {
            int c = getCount(person);
            if (books.size() == 0) return 0.0;
            return (double) c / books.size();
        }, new ProgressBarRenderer()).setId("bar").setHidden(statB).setCaption("Percent of total");


        grid.addColumn(person -> "Delete",
                new ButtonRenderer(this::clickDelete));
        grid.addColumn(person -> "Change",
                new ButtonRenderer(this::clickChange));
        grid.setWidth("40%");

        grid.setHeightMode(HeightMode.UNDEFINED);
        root.addComponent(grid);

    }

    private int getCount(Genre genre) {
        int count = 0;
        for (Book b : books)
            if (b.getGenre().equals(genre))
                count++;
        return count;

    }

    private void clickDelete(ClickableRenderer.RendererClickEvent clickEvent) {
        try {
            genreService.delete((Genre) clickEvent.getItem());
            new Notification("System message",
                    "Genre has been removed",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            grid.setItems(genreService.getAll());
        } catch (Exception e) {
            new Notification("ERROR",
                    "integrity constraint violation",
                    Notification.Type.ERROR_MESSAGE, true)
                    .show(Page.getCurrent());
        }
    }

    private void clickChange(ClickableRenderer.RendererClickEvent clickEvent) {
        sub = new GenreEditor((Genre) clickEvent.getItem());
        sub.addCloseListener(closeEvent -> grid.setItems(genreService.getAll()));
    }
}
