package org.university.ui.components.menu_panel.interfaces;

import org.university.ui.mediator.interfaces.Mediator;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
