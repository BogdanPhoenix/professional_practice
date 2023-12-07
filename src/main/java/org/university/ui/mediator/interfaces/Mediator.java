package org.university.ui.mediator.interfaces;

import org.university.business_logic.models.interfaces.Model;
import org.university.ui.components.interfaces.Component;
import org.university.ui.control_panel.interfaces.ControlPanel;

import javax.swing.*;

public interface Mediator {
    void createGUI();
    void registerComponent(Component component);
    void setControlPanel(ControlPanel controlPanel);
    void updateTableModel();
    void updateModel(Model model);
    JTable getTable();
}
