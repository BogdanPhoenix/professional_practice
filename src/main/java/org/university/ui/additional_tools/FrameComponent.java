package org.university.ui.additional_tools;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class FrameComponent {
    private static final int HEIGHT_COMPONENT = 30;
    private FrameComponent(){}

    public static @NotNull JPanel createContextPanel(JPanel @NotNull ... panels){
        JPanel contextPanel = new JPanel();
        contextPanel.setLayout(new BoxLayout(contextPanel, BoxLayout.Y_AXIS));

        for(var panel : panels){
            contextPanel.add(panel);
        }

        return contextPanel;
    }

    public static @NotNull JPanel createPanelView(@NotNull JTable table){
        JPanel panelView = new JPanel();

        Font font = new Font("Arial", Font.PLAIN, 16);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setFont(font);

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

    public static  <S> @NotNull JPanel createPanelSelect(@NotNull JComboBox<S> comboBox){
        JPanel panelControl = new JPanel(new BorderLayout());
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.X_AXIS));
        panelControl.setPreferredSize(new Dimension(0, 50));

        JPanel selectionPanel = createSelectionPanel(comboBox);

        panelControl.add(selectionPanel);
        panelControl.add(Box.createHorizontalGlue());

        selectionPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        panelControl.setBorder(new EmptyBorder(0, 10, 0, 10));

        return panelControl;
    }

    private static  <S> @NotNull JPanel createSelectionPanel(@NotNull JComboBox<S> comboBox) {
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
}
