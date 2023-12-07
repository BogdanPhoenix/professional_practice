package org.university.ui.control_panel.realization;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.InsertModelView;
import org.university.ui.mediator.interfaces.Mediator;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.business_logic.abstracts.TableModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InsertPanel implements ControlPanel {
    protected static final int MAX_HEIGHT_CONTROL_PANEL = 350;
    protected static final int MIN_HEIGHT_CONTROL_PANEL = 150;
    protected JPanel dataEntryPanel;
    protected ActionListener command;
    private InsertModelView<?> modelView;
    protected JButton button;
    private Mediator mediator;

    public InsertPanel(Mediator mediator){
        this();
        this.mediator = mediator;
    }

    public InsertPanel(){
        GRID_BAG_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        dataEntryPanel = new JPanel();
    }

    @Override
    public void repaintControlPanel(@NotNull JPanel controlPanel) {
        createControlPanel(controlPanel);

        updateButtonLogic();
        updateDataEntryPanel(controlPanel);

        int sumHeight = dataEntryPanel.getPreferredSize().height + SIZE_PANEL_BUTTON.height;
        sumHeight += sumHeight * 20 / 100;

        int maxHeight = Math.min(sumHeight, MAX_HEIGHT_CONTROL_PANEL);
        int minHeight = Math.min(maxHeight, MIN_HEIGHT_CONTROL_PANEL);
        Dimension resize = new Dimension(0, maxHeight);

        controlPanel.add(dataEntryPanel, GRID_BAG_CONSTRAINTS);

        controlPanel.setPreferredSize(resize);
        controlPanel.setMaximumSize(resize);
        controlPanel.setMinimumSize(new Dimension(0, minHeight));

        controlPanel.revalidate();
        controlPanel.repaint();
        controlPanel.setVisible(true);
    }

    private void createControlPanel(@NotNull JPanel controlPanel) {
        controlPanel.removeAll();
        JPanel panelButton = createPanelButton();

        GRID_BAG_CONSTRAINTS.weightx = 1.0;
        GRID_BAG_CONSTRAINTS.weighty = 0.0;
        GRID_BAG_CONSTRAINTS.gridy = 1;
        GRID_BAG_CONSTRAINTS.gridwidth = GridBagConstraints.REMAINDER;

        controlPanel.add(panelButton, GRID_BAG_CONSTRAINTS);

        GRID_BAG_CONSTRAINTS.insets = GRID_CONTAINERS_BORDER;

        GRID_BAG_CONSTRAINTS.weighty = 1.0;
        GRID_BAG_CONSTRAINTS.gridwidth = 1;
        GRID_BAG_CONSTRAINTS.gridy = 0;

        controlPanel.add(dataEntryPanel, GRID_BAG_CONSTRAINTS);
    }

    protected @NotNull JPanel createPanelButton(){
        JPanel panel = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(SIZE_PANEL_BUTTON);
        panel.setMaximumSize(SIZE_PANEL_BUTTON);
        panel.setMinimumSize(SIZE_PANEL_BUTTON);

        System.out.println(SIZE_PANEL_BUTTON);
        System.out.println(panel.getPreferredSize());

        button = new JButton("Додати");
        button.addActionListener(updateTable());

        panel.add(button);
        panel.setBorder(HORIZONTAL_BORDER);

        return panel;
    }

    protected void updateButtonLogic(){
        button.removeActionListener(command);
        command = modelView.commandSave();
        button.addActionListener(command);
    }

    protected void updateDataEntryPanel(@NotNull JPanel controlPanel){
        controlPanel.remove(dataEntryPanel);
        dataEntryPanel = modelView.dataEntryPanel();
    }

    @Override
    public void setModelView(TableModelView<?> modelView) {
        this.modelView = modelView;
    }

    @Contract(pure = true)
    protected @NotNull ActionListener updateTable(){
        return e -> mediator.updateTableModel();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "InsertPanel";
    }
}
