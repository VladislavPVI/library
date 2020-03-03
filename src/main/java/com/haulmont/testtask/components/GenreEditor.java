package com.haulmont.testtask.components;

import com.haulmont.testtask.domain.Genre;
import com.haulmont.testtask.service.GenreService;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

class GenreEditor extends Window {
    private VerticalLayout root = new VerticalLayout();
    private GenreService genreService = GenreService.getInstance();
    private Button save = new Button("OK", VaadinIcons.CHECK);
    private Button cancel = new Button("Cancel");
    private HorizontalLayout buttons = new HorizontalLayout(save, cancel);
    private Genre genre = new Genre();
    private static final String str = "Input must be at least 1 character. Can't include: symbols, numbers, unusual capitalization, repeating characters or punctuation.";

    private TextField name = new TextField("Name of genre");
    private Binder<Genre> binder = new Binder<>(Genre.class);


    GenreEditor(Genre genre) {
        super("Edit"); // Set window caption
        center();
        setClosable(false);

        this.genre = genre;

        binder.setBean(genre);
        setUpBinder();


        save.addClickListener(this::buttonUpdate);
        cancel.addClickListener(e -> close());

        root.addComponents(name, buttons);
        setContent(root);

        setModal(true);
        setResizable(false);
        UI.getCurrent().addWindow(this);
    }

    GenreEditor() {

        super("Add new genre"); // Set window caption
        center();
        setClosable(false);

        binder.setBean(genre);
        setUpBinder();

        save.addClickListener(this::buttonSave);
        cancel.addClickListener(e -> close());

        root.addComponents(name, buttons);
        setContent(root);

        setModal(true);
        setResizable(false);
        UI.getCurrent().addWindow(this);
    }

    private static boolean test(String s) {
        return s.trim().matches("[А-Яа-я]+") || s.trim().matches("[a-zA-Z]+");
    }

    private void setUpBinder() {

        binder.forField(name).asRequired("Enter name of genre!").
                withValidator(GenreEditor::test, str).
                bind(Genre::getName, Genre::setName);
    }

    private void buttonUpdate(Button.ClickEvent e) {
        if (!binder.isValid()) {
            new Notification("System message",
                    "Check all fields",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            return;
        }
        genreService.update(genre);
        new Notification("System message",
                "Genre has been updated",
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
        genreService.save(genre);
        new Notification("System message",
                "New genre has been added",
                Notification.Type.HUMANIZED_MESSAGE, true)
                .show(Page.getCurrent());
        close();
    }
}
