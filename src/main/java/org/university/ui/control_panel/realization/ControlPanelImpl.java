package org.university.ui.control_panel.realization;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.ui.components.menu_panel.interfaces.Component;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class ControlPanelImpl implements ControlPanel, Component {
    protected static final GridBagConstraints GRID_BAG_CONSTRAINTS = new GridBagConstraints();
    protected static final Dimension SIZE_PANEL_BUTTON = new Dimension(0, 50);
    protected static final Insets GRID_CONTAINERS_BORDER = new Insets(5, 0, 5, 0);
    protected static final EmptyBorder HORIZONTAL_BORDER = new EmptyBorder(0, 10, 0, 10);
    protected static final int MAX_HEIGHT_CONTROL_PANEL = 350;
    protected static final int MIN_HEIGHT_CONTROL_PANEL = 150;

    protected Mediator mediator;
    protected JPanel entryPanel;
    protected ActionListener command;
    protected JButton button;

    protected ControlPanelImpl(Mediator mediator){
        this.mediator = mediator;
        GRID_BAG_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        entryPanel = new JPanel();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void repaintControlPanel(@NotNull JPanel controlPanel){
        createControlPanel(controlPanel);
        updateButtonLogic();
        updateDataEntryPanel(controlPanel);
        resizeControlPanel(controlPanel);

        controlPanel.revalidate();
        controlPanel.repaint();
        controlPanel.setVisible(true);
    }

    protected void createControlPanel(@NotNull JPanel controlPanel) {
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

        controlPanel.add(entryPanel, GRID_BAG_CONSTRAINTS);
    }
    protected void updateButtonLogic(){
        button.removeActionListener(command);
        command = getCommand();
        button.addActionListener(command);
    }

    protected void updateDataEntryPanel(@NotNull JPanel controlPanel){
        controlPanel.remove(entryPanel);
        entryPanel = getEntryPanel();
        controlPanel.add(entryPanel, GRID_BAG_CONSTRAINTS);
    }

    protected void resizeControlPanel(@NotNull JPanel controlPanel){
        int sumHeight = entryPanel.getPreferredSize().height + SIZE_PANEL_BUTTON.height;
        sumHeight += sumHeight * 20 / 100;

        int maxHeight = Math.min(sumHeight, MAX_HEIGHT_CONTROL_PANEL);
        int minHeight = Math.min(maxHeight, MIN_HEIGHT_CONTROL_PANEL);
        Dimension resize = new Dimension(0, maxHeight);

        controlPanel.setPreferredSize(resize);
        controlPanel.setMaximumSize(resize);
        controlPanel.setMinimumSize(new Dimension(0, minHeight));
    }

    @Contract(pure = true)
    protected @NotNull ActionListener updateTable(){
        return e -> mediator.updateTableModel();
    }

    protected JPanel createPanelButton(){
        JPanel panel = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(SIZE_PANEL_BUTTON);
        panel.setMaximumSize(SIZE_PANEL_BUTTON);
        panel.setMinimumSize(SIZE_PANEL_BUTTON);

        var buttons = createButtons();

        for(var component : buttons){
            panel.add(component);
            panel.setBorder(HORIZONTAL_BORDER);
        }

        return panel;
    }

    protected abstract List<JButton> createButtons();
    protected abstract ActionListener getCommand();
    protected abstract JPanel getEntryPanel();
}
