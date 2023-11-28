package org.university.ui.realization.panel_interaction.control_panel;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.control_panel.ControlPanel;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class InsertPanel implements ControlPanel {
    private static final Dimension SIZE_PANEL_BUTTON = new Dimension(0, 50);
    private static final Insets GRID_CONTAINERS_BORDER = new Insets(5, 0, 5, 0);
    private static final Dimension MIN_SIZE_CONTROL_PANEL = new Dimension(0, 150);
    private static final GridBagConstraints GRID_BAG_CONSTRAINTS = new GridBagConstraints();
    private static ControlPanel instance;
    private JPanel insertPanel;

    private ActionListener command;
    private TableModelView<?> tableUtil;
    private JButton button;

    private InsertPanel(){
        GRID_BAG_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        insertPanel = new JPanel();
    }

    public static ControlPanel getInstance(){
        if(instance == null){
            instance = new InsertPanel();
        }

        return instance;
    }

    @Override
    public void repaintControlPanel(@NotNull JPanel controlPanel) {
        if(!controlPanel.isVisible()){
            createControlPanel(controlPanel);
        }

        button.removeActionListener(command);
        command = tableUtil.command();
        button.addActionListener(command);

        controlPanel.remove(insertPanel);
        insertPanel = tableUtil.panelInsertData();

        int sumHeight = insertPanel.getPreferredSize().height + SIZE_PANEL_BUTTON.height;

        int height = Math.min(sumHeight, MIN_SIZE_CONTROL_PANEL.height);
        Dimension nesSize = new Dimension(0, height);

        controlPanel.add(insertPanel, GRID_BAG_CONSTRAINTS);

        controlPanel.setPreferredSize(nesSize);
        controlPanel.setMaximumSize(nesSize);
        controlPanel.setMinimumSize(nesSize);

        controlPanel.revalidate();
        controlPanel.repaint();
        controlPanel.setVisible(true);
    }

    @Override
    public void setTableUtil(TableModelView<?> tableUtil) {
        this.tableUtil = tableUtil;
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

        controlPanel.add(insertPanel, GRID_BAG_CONSTRAINTS);

        controlPanel.setMaximumSize(MIN_SIZE_CONTROL_PANEL);
        controlPanel.setPreferredSize(MIN_SIZE_CONTROL_PANEL);
        controlPanel.setMinimumSize(MIN_SIZE_CONTROL_PANEL);

        controlPanel.revalidate();
        controlPanel.repaint();
        controlPanel.setVisible(true);
    }

    private @NotNull JPanel createPanelButton(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.GRAY);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(SIZE_PANEL_BUTTON);
        panel.setMaximumSize(SIZE_PANEL_BUTTON);
        panel.setMinimumSize(SIZE_PANEL_BUTTON);

        button = new JButton("Додати");
        button.addActionListener(updateTable());

        panel.add(button);
        panel.add(Box.createHorizontalGlue());
        panel.setBorder(new EmptyBorder(0, 10, 0, 10));

        return panel;
    }

    @Contract(pure = true)
    private @NotNull ActionListener updateTable(){
        return e -> tableUtil.repaint();
    }
}
