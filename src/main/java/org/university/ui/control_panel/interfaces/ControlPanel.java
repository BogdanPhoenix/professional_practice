package org.university.ui.control_panel.interfaces;

import org.jetbrains.annotations.NotNull;
import org.university.ui.components.interfaces.Component;
import org.university.business_logic.abstracts.TableModelView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public interface ControlPanel extends Component {
    GridBagConstraints GRID_BAG_CONSTRAINTS = new GridBagConstraints();
    Dimension SIZE_PANEL_BUTTON = new Dimension(0, 50);
    Insets GRID_CONTAINERS_BORDER = new Insets(5, 0, 5, 0);
    EmptyBorder HORIZONTAL_BORDER = new EmptyBorder(0, 10, 0, 10);

    void repaintControlPanel(@NotNull JPanel controlPanel);
    void setModelView(final TableModelView<?> modelView);
}
