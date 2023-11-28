package org.university.ui.interfaces.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.DefaultTableModel;

@FunctionalInterface
public interface SelectModelView {
    void createViewModel(@NotNull DefaultTableModel tableModel);

    default void updateTableModel(@NotNull DefaultTableModel tableModel){
        clearModel(tableModel);
        createViewModel(tableModel);
    }

    private static void clearModel(@NotNull DefaultTableModel tableModel){
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
    }
}
