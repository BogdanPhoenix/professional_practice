package org.university.ui.mediator.realization;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.models.interfaces.Model;
import org.university.ui.additional_tools.FrameComponent;
import org.university.ui.components.menu_panel.interfaces.Component;
import org.university.ui.components.menu_panel.realization.MenuBar;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Editor implements Mediator {
    private static final DefaultTableModel TABLE_MODEL = new DefaultTableModel();
    private static final GridBagConstraints GRID_BAG_CONSTRAINTS = new GridBagConstraints();

    private final ItemListener listener = this::updateMainPanel;
    private final JPanel interactionPanel;
    private final JComboBox<String> comboBox;
    private final DefaultComboBoxModel<String> comboBoxModel;
    private MenuBar menuBar;
    private Model model;
    private ControlPanel controlPanel;
    private JTable table;

    public Editor(){
        comboBox = new JComboBox<>();
        interactionPanel = new JPanel(new GridBagLayout());
        comboBoxModel = new DefaultComboBoxModel<>();
        comboBox.setModel(comboBoxModel);
        ReferenceBookModelView.setMediator(this);
    }

    @Override
    public void createGUI() {
        JFrame frame = new JFrame("Database");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(createContextPanel());
        frame.setSize(new Dimension(900, 900));
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }

    private @NotNull JPanel createContextPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        table = new JTable(TABLE_MODEL);
        JPanel panelView = FrameComponent.createPanelView(table);
        JPanel panelSelectTable = FrameComponent.createPanelSelect(comboBox);

        updateModel(model);
        comboBox.addItemListener(listener);
        controlPanel.repaintControlPanel(interactionPanel);

        GRID_BAG_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        GRID_BAG_CONSTRAINTS.weightx = 1.0;
        GRID_BAG_CONSTRAINTS.weighty = 0.0;
        GRID_BAG_CONSTRAINTS.gridwidth = GridBagConstraints.REMAINDER;

        mainPanel.add(panelSelectTable, GRID_BAG_CONSTRAINTS);

        GRID_BAG_CONSTRAINTS.gridy = 2;
        mainPanel.add(interactionPanel, GRID_BAG_CONSTRAINTS);

        GRID_BAG_CONSTRAINTS.weighty = 1.0;
        GRID_BAG_CONSTRAINTS.gridwidth = 1;
        GRID_BAG_CONSTRAINTS.gridy = 1;
        mainPanel.add(panelView, GRID_BAG_CONSTRAINTS);

        return mainPanel;
    }

    @Override
    public void updateModel(@NotNull Model model) {
        this.model = model;
        model.setMediator(this);
        var values = model.valuesModel();

        comboBoxModel.removeAllElements();
        comboBoxModel.addAll(values);
        comboBoxModel.setSelectedItem(values.get(0));
    }

    @Override
    public JTable getTable() {
        return table;
    }

    private void updateMainPanel(@NotNull ItemEvent e){
        if(e.getStateChange() != ItemEvent.SELECTED){
            return;
        }

        String item = (String) e.getItem();

        if(item == null || item.isEmpty()){
            return;
        }

        var optional = model.valueOfString(item);

        if(optional.isEmpty()){
            return;
        }

        var tableUtil = optional.get();

        tableUtil.updateTableModel(TABLE_MODEL);
        controlPanel.setModelView(tableUtil);
        controlPanel.repaintControlPanel(interactionPanel);
    }

    private void activeUpdate(){
        listener.itemStateChanged(new ItemEvent(comboBox, ItemEvent.SELECTED, comboBox.getSelectedItem(), ItemEvent.SELECTED));
    }

    @Override
    public void registerComponent(@NotNull Component component) {
        component.setMediator(this);

        if (component.getName().equals("MenuBar")) {
            menuBar = (MenuBar) component;
        }
    }

    @Override
    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        activeUpdate();
    }

    @Override
    public void updateTableModel() {
        activeUpdate();
    }
}
