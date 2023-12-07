package org.university.ui.mediator.realization;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.models.interfaces.Model;
import org.university.ui.components.interfaces.Component;
import org.university.ui.components.realization.MenuBar;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.business_logic.abstracts.TableModelView;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Editor implements Mediator {
    private static final int HEIGHT_COMPONENT = 30;
    private static final DefaultTableModel TABLE_MODEL = new DefaultTableModel();
    private static final Insets GRID_CONTAINERS_BORDER = new Insets(5, 0, 5, 0);
    private static final GridBagConstraints GRID_BAG_CONSTRAINTS = new GridBagConstraints();

    private final ItemListener listener = this::updateMainPanel;
    private MenuBar menuBar;
    private Model model;
    private ControlPanel controlPanel;
    private JTable table;
    private final JPanel interactionPanel;
    private final JComboBox<String> comboBox;
    private final DefaultComboBoxModel<String> comboBoxModel;

    public Editor(){
        comboBox = new JComboBox<>();
        interactionPanel = new JPanel(new GridBagLayout());
        comboBoxModel = new DefaultComboBoxModel<>();
        comboBox.setModel(comboBoxModel);
        TableModelView.setMediator(this);
    }

    @Override
    public void createGUI() {
        JFrame frame = new JFrame("Database");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(mainPanel());
        frame.setSize(new Dimension(900, 900));
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }

    private @NotNull JPanel mainPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel panelView = createPanelView();
        JPanel panelSelectTable = createPanelSelectTable();

        updateModel(model);
        comboBox.addItemListener(listener);
        controlPanel.repaintControlPanel(interactionPanel);

        GRID_BAG_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        GRID_BAG_CONSTRAINTS.weightx = 1.0;
        GRID_BAG_CONSTRAINTS.weighty = 0.0;
        GRID_BAG_CONSTRAINTS.gridwidth = GridBagConstraints.REMAINDER;

        mainPanel.add(panelSelectTable, GRID_BAG_CONSTRAINTS);
        GRID_BAG_CONSTRAINTS.insets = GRID_CONTAINERS_BORDER;

        GRID_BAG_CONSTRAINTS.gridy = 2;
        mainPanel.add(interactionPanel, GRID_BAG_CONSTRAINTS);

        GRID_BAG_CONSTRAINTS.weighty = 1.0;
        GRID_BAG_CONSTRAINTS.gridwidth = 1;
        GRID_BAG_CONSTRAINTS.gridy = 1;
        mainPanel.add(panelView, GRID_BAG_CONSTRAINTS);

        return mainPanel;
    }

    @Contract(pure = true)
    private @NotNull JPanel createPanelView(){
        JPanel panelView = new JPanel();

        Font font = new Font("Arial", Font.PLAIN, 16);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setFont(font);

        table = new JTable(TABLE_MODEL);
        table.setFont(font);
        table.setDefaultRenderer(Object.class, renderer);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setDefaultEditor(Object.class, null);

        panelView.setLayout(new BorderLayout());
        panelView.add(new JScrollPane(table), BorderLayout.CENTER);

        return panelView;
    }

    private @NotNull JPanel createPanelSelectTable(){
        JPanel panelControl = new JPanel(new BorderLayout());
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.X_AXIS));
        panelControl.setPreferredSize(new Dimension(0, 50));

        JPanel selectionPanel = createSelectionPanel();

        panelControl.add(selectionPanel);
        panelControl.add(Box.createHorizontalGlue());

        selectionPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        panelControl.setBorder(new EmptyBorder(0, 10, 0, 10));

        return panelControl;
    }

    private @NotNull JPanel createSelectionPanel() {
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));

        JLabel title = new JLabel("Оберіть таблицю: ");

        title.setPreferredSize(new Dimension(0, HEIGHT_COMPONENT));
        comboBox.setMaximumSize(new Dimension(250, HEIGHT_COMPONENT));

        title.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        comboBox.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        selectionPanel.add(title);
        selectionPanel.add(comboBox);

        Dimension size = new Dimension(
                title.getMaximumSize().width + comboBox.getMaximumSize().width,
                HEIGHT_COMPONENT
        );

        selectionPanel.setMaximumSize(size);
        selectionPanel.setPreferredSize(size);

        return selectionPanel;
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
