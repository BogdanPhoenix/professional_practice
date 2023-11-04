package org.university.ui.interfaces.window.abstract_factory.panel_window;

import org.university.ui.interfaces.panel_interaction.strategy.PanelStrategy;

public interface GUIFactory {
    MenuBar getMenuBar();
    PanelStrategy getInteraction();
}
