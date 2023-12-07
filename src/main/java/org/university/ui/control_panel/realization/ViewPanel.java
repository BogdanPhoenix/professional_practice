package org.university.ui.control_panel.realization;

import org.jetbrains.annotations.NotNull;
import org.university.ui.mediator.interfaces.Mediator;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.business_logic.abstracts.TableModelView;

import javax.swing.*;

public class ViewPanel implements ControlPanel {
    private Mediator mediator;

    public ViewPanel(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void repaintControlPanel(@NotNull JPanel controlPanel) {
        if(controlPanel.isVisible()){
            controlPanel.removeAll();
        }
        controlPanel.setVisible(false);
    }

    @Override
    public void setModelView(TableModelView<?> modelView) {
        // TODO document why this method is empty
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ViewPanel";
    }
}
