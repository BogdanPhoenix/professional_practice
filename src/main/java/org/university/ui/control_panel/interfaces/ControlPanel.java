package org.university.ui.control_panel.interfaces;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.ReferenceBookModelView;

import javax.swing.*;

public interface ControlPanel {
    void repaintControlPanel(@NotNull JPanel controlPanel);
    void setModelView(final ReferenceBookModelView<?> modelView);
}
