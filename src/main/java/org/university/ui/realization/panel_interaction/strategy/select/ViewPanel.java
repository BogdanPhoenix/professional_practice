package org.university.ui.realization.panel_interaction.strategy.select;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.Table;
import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.ui.interfaces.panel_interaction.strategy.PanelStrategy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ViewPanel implements PanelStrategy {
    private static final int HEIGHT_COMPONENT = 30;

    @Override
    public JPanel createPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel panelControl = createPanelControl();
        JPanel panelView = createPanelView();

        mainPanel.add(panelControl, BorderLayout.NORTH);
        mainPanel.add(panelView, BorderLayout.CENTER);

        return mainPanel;
    }

    private @NotNull JPanel createPanelControl(){
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
        String titleText = "Оберіть таблицю:";
        float alignment = Component.LEFT_ALIGNMENT;

        return createSubPanel(titleText, alignment, createTableComboBox());
    }

    private @NotNull JPanel createSubPanel(String titleText, float alignment, JComboBox<?> comboBox) {
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));

        JLabel title = new JLabel(titleText);

        title.setPreferredSize(new Dimension(0, HEIGHT_COMPONENT));
        comboBox.setMaximumSize(new Dimension(200, HEIGHT_COMPONENT));

        title.setAlignmentX(alignment);
        comboBox.setAlignmentX(alignment);

        selectionPanel.add(title);
        selectionPanel.add(comboBox);

        Dimension size = new Dimension(title.getMaximumSize().width + comboBox.getMaximumSize().width, HEIGHT_COMPONENT);
        selectionPanel.setMaximumSize(size);
        selectionPanel.setPreferredSize(size);

        return selectionPanel;
    }

    private @NotNull JComboBox<Table> createTableComboBox(){
        ItemListener listener = e ->{
            if(e.getStateChange() != ItemEvent.SELECTED){
                return;
            }
            Table table = (Table) e.getItem();

            if(table == null){
                return;
            }

            table.getTableModel().updateTableModel();
        };

        JComboBox<Table> comboBox = new JComboBox<>(Table.values());
        comboBox.addItemListener(listener);
        listener.itemStateChanged(new ItemEvent(comboBox, ItemEvent.SELECTED, comboBox.getSelectedItem(), ItemEvent.SELECTED));

        return comboBox;
    }

    private @NotNull JPanel createPanelView() {
        JPanel panelView = new JPanel();

        JTable table = new JTable(TableModelView.getTableModel());
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setEnabled(false);

        panelView.setLayout(new BorderLayout());
        panelView.add(new JScrollPane(table), BorderLayout.CENTER);

        return panelView;
    }
}
