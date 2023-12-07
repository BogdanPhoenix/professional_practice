package org.university.ui.components.realization;

import org.university.ui.components.interfaces.Component;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;

public class Menu extends JMenu implements Component {
    private transient Mediator mediator;

    public Menu(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Menu";
    }
}
