package com.haulmont.testtask.components;

import com.haulmont.testtask.domain.Author;
import com.haulmont.testtask.service.AuthorService;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

class AuthorEditor extends Window {

    private VerticalLayout root = new VerticalLayout();
    private AuthorService authorService = AuthorService.getInstance();
    private Button save = new Button("OK", VaadinIcons.CHECK);
    private Button cancel = new Button("Cancel");
    private HorizontalLayout buttons = new HorizontalLayout(save, cancel);
    private Author author = new Author();
    private static final String str = "Input must be at least 1 character. Can't include: symbols, numbers, unusual capitalization, repeating characters or punctuation.";

    private TextField lastName = new TextField("Last name");
    private TextField firstName = new TextField("First name");
    private TextField patronymic = new TextField("Patronymic name");

    private Binder<Author> binder = new Binder<>(Author.class);


    AuthorEditor(Author author) {
        super("Edit"); // Set window caption
        center();
        setClosable(false);

        this.author = author;

        binder.setBean(author);
        setUpBinder();

        save.addClickListener(this::buttonUpdate);
        cancel.addClickListener(e -> close());

        root.addComponents(lastName, firstName, patronymic, buttons);
        setContent(root);

        setModal(true);
        setResizable(false);
        UI.getCurrent().addWindow(this);
    }

    AuthorEditor() {

        super("Add new author"); // Set window caption
        center();
        setClosable(false);

        binder.setBean(author);
        setUpBinder();

        save.addClickListener(this::buttonSave);
        cancel.addClickListener(e -> close());

        root.addComponents(lastName, firstName, patronymic, buttons);
        setContent(root);

        setModal(true);
        setResizable(false);
        UI.getCurrent().addWindow(this);
    }

    private static boolean test(String s) {
        return s.trim().matches("[А-Яа-я]+") || s.trim().matches("[a-zA-Z]+");
    }

    private void setUpBinder() {

        binder.forField(lastName).asRequired("Enter lastname!").
                withValidator(AuthorEditor::test, str).
                bind(Author::getLastName, Author::setLastName);
        binder.forField(firstName).asRequired("Enter firstname!").
                withValidator(AuthorEditor::test, str).
                bind(Author::getFirstName, Author::setFirstName);
        binder.forField(patronymic).asRequired("Enter patronymic!").
                withValidator(AuthorEditor::test, str).
                bind(Author::getPatronymic, Author::setPatronymic);


    }

    private void buttonUpdate(Button.ClickEvent e) {
        if (!binder.isValid()) {
            new Notification("System message",
                    "Check all fields",
                    Notification.Type.WARNING_MESSAGE, true)
                    .show(Page.getCurrent());
            return;
        }
        authorService.update(author);
        new Notification("System message",
                "Author has been updated",
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
        authorService.save(author);
        new Notification("System message",
                "New author has been added",
                Notification.Type.HUMANIZED_MESSAGE, true)
                .show(Page.getCurrent());
        close();
    }
}