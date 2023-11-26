package org.university.ui.interfaces.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.DefaultTableModel;

@FunctionalInterface
public interface SelectModelView {
    void createModel(DefaultTableModel tableModel);

    default void updateTableModel(DefaultTableModel tableModel){
        clearModel(tableModel);
        createModel(tableModel);
    }

    private static void clearModel(@NotNull DefaultTableModel tableModel){
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
    }
}
