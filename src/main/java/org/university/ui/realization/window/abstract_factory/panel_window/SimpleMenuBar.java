package org.university.ui.realization.window.abstract_factory.panel_window;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.window.abstract_factory.menubar.MenuBarFactory;
import org.university.ui.interfaces.window.abstract_factory.panel_window.MenuBar;
import org.university.ui.realization.window.abstract_factory.menubar.SimpleMenuBarFactory;

import javax.swing.*;

public class SimpleMenuBar implements MenuBar {
    @Override
    public JMenuBar createMenu() {
        MenuBarFactory factory = new SimpleMenuBarFactory();

        return createMenuBar(
                factory.getActiveMenu().create()
        );
    }

    private @NotNull JMenuBar createMenuBar(JMenu @NotNull ... menu){
        JMenuBar menuBar = new JMenuBar();

        for(JMenu el : menu){
            menuBar.add(el);
        }

        return menuBar;
    }
}
