package org.university.ui.components.realization;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.models.interfaces.Model;
import org.university.ui.components.interfaces.Component;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuItem extends JMenuItem implements Component {
    private transient Mediator mediator;
    private final transient Model modelView;
    private final transient ControlPanel controlPanel;

    public MenuItem(Mediator mediator, ControlPanel controlPanel, Model modelView) {
        this.mediator = mediator;
        this.modelView = modelView;
        this.controlPanel = controlPanel;
        this.addActionListener(createListener());
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "MenuItem";
    }

    @Contract(pure = true)
    private @NotNull ActionListener createListener(){
        return e -> {
            mediator.updateModel(modelView);
            mediator.setControlPanel(controlPanel);
        };
    }
}
