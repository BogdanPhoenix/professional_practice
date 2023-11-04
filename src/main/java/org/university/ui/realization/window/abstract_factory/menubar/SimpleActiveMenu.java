package org.university.ui.realization.window.abstract_factory.menubar;

import org.university.ui.interfaces.window.abstract_factory.menubar.ActiveMenu;

import javax.swing.*;

public class SimpleActiveMenu implements ActiveMenu {
    @Override
    public JMenu create() {
        JMenu menu = new JMenu("Дії");

        JMenuItem viewItem = new JMenuItem("Перегля");
        JMenuItem editItem = new JMenuItem("Редагування");
        JMenuItem createItem = new JMenuItem("Створити");
        JMenuItem deleteItem = new JMenuItem("Видалити");

        menu.add(viewItem);
        menu.add(editItem);
        menu.add(createItem);
        menu.add(deleteItem);

        return menu;
    }
}
