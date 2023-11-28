package org.university.ui.realization.panel_interaction.logic.view;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.view.FullTaskInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class FullTaskInfoUtil extends TableModelView<FullTaskInfo> {
    public FullTaskInfoUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Відповідальний", "Виконання", "Пріоритет", "Складність", "Дата старту виконання", "Запланована дата завершення", "Дата завершення", "Опис");
        nameTable = "Завдання";
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var fullTaskInfos = selectAll();
        addRows(tableModel, fullTaskInfos);
    }

    @Override
    protected Object[] createAttribute(@NotNull FullTaskInfo value) {
        return new Object[]{
                value.getNameProject(),
                value.getNameSprint(),
                value.getNameTask(),
                value.getFullNameUser(),
                value.getNameExecution(),
                value.getNamePriority(),
                value.getNameComplexity(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    public JPanel panelInsertData() {
        return new JPanel();
    }

    @Override
    public ActionListener command() {
        return e -> {};
    }

    @Override
    public Class<FullTaskInfo> resolveEntityClass() {
        return FullTaskInfo.class;
    }
}
