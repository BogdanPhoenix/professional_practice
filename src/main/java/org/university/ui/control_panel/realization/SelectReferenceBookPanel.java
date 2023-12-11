package org.university.ui.control_panel.realization;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.ui.control_panel.interfaces.ControlPanel;

import javax.swing.*;

public class SelectReferenceBookPanel implements ControlPanel {
    @Override
    public void repaintControlPanel(@NotNull JPanel controlPanel) {
        if(controlPanel.isVisible()){
            controlPanel.removeAll();
        }
        controlPanel.setVisible(false);
    }
    @Override
    public void setModelView(ReferenceBookModelView<?> modelView) {
        /* TODO this class is used only with reference books that do not need to implement access to the editor table using additional search parameters */
    }
}
