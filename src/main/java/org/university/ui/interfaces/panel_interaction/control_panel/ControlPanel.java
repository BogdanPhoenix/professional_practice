package org.university.ui.interfaces.panel_interaction.control_panel;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;

public interface ControlPanel {
    void repaintControlPanel(@NotNull JPanel controlPanel);
    void setTableUtil(final TableModelView<?> tableUtil);
}
