package org.university.ui.realization.window.abstract_factory.menubar;

import org.university.ui.interfaces.window.abstract_factory.menubar.SourceInfoMenu;

import javax.swing.*;

public class SimpleSourceInfoMenu implements SourceInfoMenu {
    @Override
    public JMenu create() {
        JMenu menu = new JMenu("Інформація");

        JMenuItem projectItem = new JMenuItem("Проекти");
        JMenuItem sprintItem = new JMenuItem("Спрінти");
        JMenuItem taskItem = new JMenuItem("Завдання");
        JMenuItem userItem = new JMenuItem("Співробітники");

        menu.add(projectItem);
        menu.add(sprintItem);
        menu.add(taskItem);
        menu.add(userItem);

        return menu;
    }
}
