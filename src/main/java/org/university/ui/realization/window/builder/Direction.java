package org.university.ui.realization.window.builder;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.window.abstract_factory.panel_window.GUIFactory;
import org.university.ui.interfaces.window.builder.Builder;
import org.university.ui.realization.window.abstract_factory.panel_window.SimpleGUIFactory;

import java.awt.*;

public class Direction {
    public void createSimpleWindow(@NotNull Builder builder){
        GUIFactory factory = new SimpleGUIFactory();

        builder.setTitle("Database");
        builder.setSize(new Dimension(900, 900));
        builder.setMinSize(new Dimension(400, 400));
        builder.setMenuBar(factory.getMenuBar().createMenu());
        builder.setPanelInteraction(factory.getInteraction());
    }
}
