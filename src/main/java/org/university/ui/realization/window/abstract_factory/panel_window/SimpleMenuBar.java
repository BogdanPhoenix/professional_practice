package org.university.ui.realization.window.abstract_factory.panel_window;

import org.university.ui.interfaces.window.abstract_factory.menubar.MenuBarFactory;
import org.university.ui.interfaces.window.abstract_factory.panel_window.MenuBar;
import org.university.ui.realization.window.abstract_factory.menubar.SimpleFactory;

import javax.swing.*;

public class SimpleMenuBar implements MenuBar {
    @Override
    public JMenuBar createMenu() {
        MenuBarFactory factory = new SimpleFactory();
        JMenuBar menuBar = new JMenuBar();

        addMenu(menuBar,
                factory.getSourceInfoMenu().create(),
                factory.getActiveMenu().create()
        );

        return menuBar;
    }

    private void addMenu(JMenuBar menuBar, JMenu... menu){
        for(JMenu el : menu){
            menuBar.add(el);
        }
    }
}
