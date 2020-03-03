package com.haulmont.testtask;

import com.haulmont.testtask.components.AuthorView;
import com.haulmont.testtask.components.BookView;
import com.haulmont.testtask.components.GenreView;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Title("Library")
@PushStateNavigation
public class MenuUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Label title = new Label("Library");
        title.addStyleName(ValoTheme.MENU_TITLE);

        Button view1 = new Button("Books", e -> getNavigator().navigateTo(""));
        view1.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button view2 = new Button("Authors", e -> getNavigator().navigateTo("authors"));
        view2.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button view3 = new Button("Genres", e -> getNavigator().navigateTo("genres"));
        view3.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);


        CssLayout menu = new CssLayout(title, view1, view2, view3);
        menu.addStyleName(ValoTheme.MENU_ROOT);

        AbsoluteLayout viewCont = new AbsoluteLayout();
        HorizontalLayout mainLayout = new HorizontalLayout();

        mainLayout.addComponents(menu, viewCont);
        mainLayout.setExpandRatio(viewCont, 1);
        mainLayout.setSizeFull();
        setContent(mainLayout);

        Navigator navigator = new Navigator(this, viewCont);
        navigator.addView("", BookView.class);
        navigator.addView("authors", AuthorView.class);
        navigator.addView("genres", GenreView.class);


    }
}
