package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.PriorityTask;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class PriorityTaskUtil extends TableModelView<PriorityTask> {
    private static final String NAME_PRIORITY = "namePriority";

    public PriorityTaskUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Пріоритети завдання";
    }

    @Override
    public Class<PriorityTask> resolveEntityClass() {
        return PriorityTask.class;
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var priorityTasks = selectAll();
        addRows(tableModel, priorityTasks);
    }

    @Override
    protected Object[] createAttribute(@NotNull PriorityTask value) {
        return new Object[]{ value.getNamePriority() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel(NAME_PRIORITY);
    }

    @Override
    public ActionListener command() {
        return e -> {};
    }
}
