package org.university.ui.realization.panel_interaction.strategy;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.Table;
import org.university.ui.interfaces.panel_interaction.control_panel.ControlPanel;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.ui.interfaces.panel_interaction.strategy.StrategyControlPanel;
import org.university.ui.realization.panel_interaction.control_panel.ViewPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainPanel extends JPanel implements StrategyControlPanel {
    private static MainPanel instance;
    private static final int HEIGHT_COMPONENT = 30;
    private static final Insets GRID_CONTAINERS_BORDER = new Insets(5, 0, 5, 0);
    private static final DefaultTableModel TABLE_MODEL = new DefaultTableModel();
    private static final GridBagConstraints GRID_BAG_CONSTRAINTS = new GridBagConstraints();

    private final JPanel controlPanel;
    private transient ControlPanel instrumentControlPanel;
    private final transient ItemListener listener = this::updateMainPanel;
    private JComboBox<Table> comboBox;

    private MainPanel(){
        controlPanel = new JPanel(new GridBagLayout());
        instrumentControlPanel = ViewPanel.getInstance();

        this.setLayout(new GridBagLayout());

        JPanel panelView = createPanelView();
        JPanel panelSelectTable = createPanelSelectTable();
        instrumentControlPanel.repaintControlPanel(controlPanel);

        GRID_BAG_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        GRID_BAG_CONSTRAINTS.weightx = 1.0;
        GRID_BAG_CONSTRAINTS.weighty = 0.0;
        GRID_BAG_CONSTRAINTS.gridwidth = GridBagConstraints.REMAINDER;

        this.add(panelSelectTable, GRID_BAG_CONSTRAINTS);

        GRID_BAG_CONSTRAINTS.insets = GRID_CONTAINERS_BORDER;

        GRID_BAG_CONSTRAINTS.weighty = 1.0;
        GRID_BAG_CONSTRAINTS.gridwidth = 1;
        GRID_BAG_CONSTRAINTS.gridy = 1;
        this.add(panelView, GRID_BAG_CONSTRAINTS);

        GRID_BAG_CONSTRAINTS.gridy = 2;
        this.add(controlPanel, GRID_BAG_CONSTRAINTS);
    }

    public static MainPanel getInstance(){
        if(instance == null){
            instance = new MainPanel();
        }

        return instance;
    }

    @Override
    public void setControlPanel(final ControlPanel controlPanel) {
        this.instrumentControlPanel = controlPanel;
        activeUpdate();
    }

    private @NotNull JPanel createPanelSelectTable(){
        JPanel panelControl = new JPanel(new BorderLayout());
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.X_AXIS));
        panelControl.setPreferredSize(new Dimension(0, 50));

        JPanel selectionPanel = createSelectionPanel();

        panelControl.add(selectionPanel);
        panelControl.add(Box.createHorizontalGlue());

        selectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelControl.setBorder(new EmptyBorder(0, 10, 0, 10));

        return panelControl;
    }

    private @NotNull JPanel createSelectionPanel() {
        createTableComboBox();
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));

        JLabel title = new JLabel("Оберіть таблицю: ");

        title.setPreferredSize(new Dimension(0, HEIGHT_COMPONENT));
        comboBox.setMaximumSize(new Dimension(250, HEIGHT_COMPONENT));

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        selectionPanel.add(title);
        selectionPanel.add(comboBox);

        Dimension size = new Dimension(title.getMaximumSize().width + comboBox.getMaximumSize().width, HEIGHT_COMPONENT);
        selectionPanel.setMaximumSize(size);
        selectionPanel.setPreferredSize(size);

        return selectionPanel;
    }

    private void createTableComboBox(){
        comboBox = new JComboBox<>(Table.values());
        comboBox.addItemListener(listener);
        activeUpdate();
    }

    private void activeUpdate(){
        listener.itemStateChanged(new ItemEvent(comboBox, ItemEvent.SELECTED, comboBox.getSelectedItem(), ItemEvent.SELECTED));
    }

    private void updateMainPanel(@NotNull ItemEvent e){
        if(e.getStateChange() != ItemEvent.SELECTED){
            return;
        }

        Table table = (Table) e.getItem();

        if(table == null){
            return;
        }

        TableModelView<?> tableUtil = table.getTableUtil();

        tableUtil.updateTableModel(TABLE_MODEL);
        instrumentControlPanel.setTableUtil(tableUtil);
        instrumentControlPanel.repaintControlPanel(controlPanel);
    }

    @Contract(pure = true)
    private @NotNull JPanel createPanelView(){
        JPanel panelView = new JPanel();

        JTable table = new JTable(TABLE_MODEL);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setEnabled(false);

        panelView.setLayout(new BorderLayout());
        panelView.add(new JScrollPane(table), BorderLayout.CENTER);

        return panelView;
    }
}
