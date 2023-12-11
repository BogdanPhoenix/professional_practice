package org.university.ui.control_panel.realization;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.DeleteModelView;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class DeletePanel extends ControlPanelImpl {
    private DeleteModelView<?> modelView;

    public DeletePanel(Mediator mediator) {
        super(mediator);
    }

    @Override
    public String getName() {
        return "DeletePanel";
    }

    @Override
    protected void createControlPanel(@NotNull JPanel controlPanel){
        controlPanel.removeAll();
        JPanel panelButton = createPanelButton();

        GRID_BAG_CONSTRAINTS.weighty = 1.0;
        GRID_BAG_CONSTRAINTS.weightx = 1.0;
        GRID_BAG_CONSTRAINTS.gridy = 0;
        GRID_BAG_CONSTRAINTS.gridwidth = GridBagConstraints.REMAINDER;

        controlPanel.add(panelButton, GRID_BAG_CONSTRAINTS);
    }

    @Override
    protected ActionListener getCommand() {
        return modelView.commandDelete();
    }

    @Override
    protected JPanel getEntryPanel() {
        return new JPanel();
    }

    @Override
    public void setModelView(ReferenceBookModelView<?> modelView) {
        this.modelView = modelView;
    }

    @Override
    protected List<JButton> createButtons() {
        button = new JButton("Видалити");
        button.addActionListener(updateTable());

        return List.of(button);
    }
}
