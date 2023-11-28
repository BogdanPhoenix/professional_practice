package org.university.ui.realization.panel_interaction.control_panel;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.control_panel.ControlPanel;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;

public class ViewPanel implements ControlPanel {
    private static ControlPanel instance;

    private ViewPanel(){}

    public static ControlPanel getInstance() {
        if(instance == null){
            instance = new ViewPanel();
        }
        return instance;
    }

    @Override
    public void repaintControlPanel(@NotNull JPanel controlPanel) {
        if(controlPanel.isVisible()){
            controlPanel.removeAll();
        }
        controlPanel.setVisible(false);
    }

    @Override
    public void setTableUtil(TableModelView<?> tableUtil) {
        // TODO document why this method is empty
    }
}
