package org.university.ui.realization.window.abstract_factory.panel_window;

import org.university.ui.interfaces.panel_interaction.strategy.PanelStrategy;
import org.university.ui.interfaces.window.abstract_factory.panel_window.GUIFactory;
import org.university.ui.interfaces.window.abstract_factory.panel_window.MenuBar;
import org.university.ui.realization.panel_interaction.strategy.select.ViewPanel;

public class SimpleFactory implements GUIFactory {

    @Override
    public MenuBar getMenuBar() {
        return new SimpleMenuBar();
    }

    @Override
    public PanelStrategy getInteraction() {
        return new ViewPanel();
    }
}
