package com.haulmont.testtask.components;

import com.haulmont.testtask.domain.Author;
import com.haulmont.testtask.service.AuthorService;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class AuthorView extends Composite implements View {


    private VerticalLayout root;
    private AuthorService authorService = AuthorService.getInstance();
    private Grid<Author> grid = new Grid<>();
    private final Button addNewButton = new Button("New author", VaadinIcons.PLUS);

    public AuthorView() {
        setupLayout();
        addHeader();
        addTable();
    }

    private void addHeader() {
        Label header = new Label("Authors");
        header.addStyleName(ValoTheme.LABEL_H2);
        addNewButton.addClickListener(e -> {
            AuthorEditor sub = new AuthorEditor();
            sub.addCloseListener(closeEvent -> grid.setItems(authorService.getAll()));

        });
        root.addComponent(header);
        root.addComponent(addNewButton);

    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        setCompositionRoot(root);
    }


    private void addTable() {

        grid.setItems(authorService.getAll());
        grid.addColumn(Author::toString)
                .setCaption("Full Name").setId("fullName");
        grid.addColumn(person -> "Delete",
                new ButtonRenderer(this::clickDelete));
        grid.addColumn(person -> "Change",
                new ButtonRenderer(this::clickChange));

        grid.setHeightMode(HeightMode.UNDEFINED);
        root.addComponent(grid);

    }


    private void clickDelete(ClickableRenderer.RendererClickEvent clickEvent) {
        try {
            authorService.delete((Author) clickEvent.getItem());
            new Notification("System message",
                    "Author has been removed",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            grid.setItems(authorService.getAll());
        } catch (Exception e) {
            new Notification("ERROR",
                    "integrity constraint violation",
                    Notification.Type.ERROR_MESSAGE, true)
                    .show(Page.getCurrent());
        }
    }

    private void clickChange(ClickableRenderer.RendererClickEvent clickEvent) {
        AuthorEditor sub = new AuthorEditor((Author) clickEvent.getItem());
        sub.addCloseListener(closeEvent -> grid.setItems(authorService.getAll()));
    }
}