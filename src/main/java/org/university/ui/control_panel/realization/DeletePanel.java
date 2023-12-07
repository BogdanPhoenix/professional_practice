package org.university.ui.control_panel.realization;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.business_logic.abstracts.DeleteModelView;
import org.university.business_logic.abstracts.TableModelView;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DeletePanel implements ControlPanel {
    private JButton button;
    private Mediator mediator;
    private ActionListener command;
    private DeleteModelView<?> modelView;

    public DeletePanel(Mediator mediator){
        this.mediator = mediator;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "DeletePanel";
    }

    @Override
    public void repaintControlPanel(@NotNull JPanel controlPanel) {
        controlPanel.removeAll();
        JPanel panelButton = createPanelButton();

        GRID_BAG_CONSTRAINTS.weightx = 1.0;
        GRID_BAG_CONSTRAINTS.gridwidth = GridBagConstraints.REMAINDER;

        controlPanel.add(panelButton, GRID_BAG_CONSTRAINTS);

        button.removeActionListener(command);
        command = modelView.commandDelete();
        button.addActionListener(command);

        controlPanel.setPreferredSize(SIZE_PANEL_BUTTON);
        controlPanel.setMaximumSize(SIZE_PANEL_BUTTON);
        controlPanel.setMinimumSize(SIZE_PANEL_BUTTON);

        controlPanel.revalidate();
        controlPanel.repaint();
        controlPanel.setVisible(true);
    }

    private @NotNull JPanel createPanelButton(){
        JPanel panel = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(SIZE_PANEL_BUTTON);
        panel.setMaximumSize(SIZE_PANEL_BUTTON);
        panel.setMinimumSize(SIZE_PANEL_BUTTON);

        button = new JButton("Видалити");
        button.addActionListener(updateTable());

        panel.add(button);
        panel.add(Box.createHorizontalGlue());
        panel.setBorder(HORIZONTAL_BORDER);

        return panel;
    }

    @Contract(pure = true)
    private @NotNull ActionListener updateTable(){
        return e -> mediator.updateTableModel();
    }

    @Override
    public void setModelView(TableModelView<?> modelView) {
        this.modelView = modelView;
    }
}
