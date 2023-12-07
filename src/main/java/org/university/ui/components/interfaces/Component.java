package org.university.ui.components.interfaces;

import org.university.ui.mediator.interfaces.Mediator;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
