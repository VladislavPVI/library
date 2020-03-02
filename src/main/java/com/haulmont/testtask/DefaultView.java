package com.haulmont.testtask;

        import com.vaadin.navigator.View;
        import com.vaadin.ui.AbstractComponent;
        import com.vaadin.ui.Component;
        import com.vaadin.ui.Composite;
        import com.vaadin.ui.Label;

public class DefaultView extends Composite implements View {
    public DefaultView() {

        setCompositionRoot(new Label("Default view"));
    }
}
