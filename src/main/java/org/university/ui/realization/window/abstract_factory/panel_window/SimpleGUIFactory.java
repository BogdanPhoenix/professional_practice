package org.university.ui.realization.window.abstract_factory.panel_window;

import org.university.ui.interfaces.panel_interaction.strategy.MainPanel;
import org.university.ui.interfaces.window.abstract_factory.panel_window.GUIFactory;
import org.university.ui.interfaces.window.abstract_factory.panel_window.MenuBar;

import javax.swing.*;

public class SimpleGUIFactory implements GUIFactory {

    @Override
    public MenuBar getMenuBar() {
        return new SimpleMenuBar();
    }

    @Override
    public JPanel getInteraction() {
        return MainPanel.getInstance();
    }
}
