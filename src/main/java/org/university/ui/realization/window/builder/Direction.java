package org.university.ui.realization.window.builder;

import org.university.ui.interfaces.window.abstract_factory.panel_window.GUIFactory;
import org.university.ui.interfaces.window.builder.Builder;
import org.university.ui.realization.window.abstract_factory.panel_window.SimpleFactory;

import java.awt.*;

public class Direction {
    public void createSimpleWindow(Builder builder){
        GUIFactory factory = new SimpleFactory();

        builder.setTitle("Database");
        builder.setSize(new Dimension(900, 900));
        builder.setMenuBar(factory.getMenuBar().createMenu());
        builder.setPanelInteraction(factory.getInteraction().createPanel());
    }
}
