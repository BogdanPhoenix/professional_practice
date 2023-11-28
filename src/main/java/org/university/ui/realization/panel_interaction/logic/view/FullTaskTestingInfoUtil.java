package org.university.ui.realization.panel_interaction.logic.view;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.view.FullTaskTestingInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class FullTaskTestingInfoUtil extends TableModelView<FullTaskTestingInfo> {
    public FullTaskTestingInfoUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Тестове завдання", "Відповідальний", "Виконання", "Пріоритет", "Дата старту виконання", "Запланована дата завершення", "Дата завершення", "Опис");
        nameTable = "Завдання на тестування";
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var fullTaskTestingInfos = selectAll();
        addRows(tableModel, fullTaskTestingInfos);
    }

    @Override
    protected Object[] createAttribute(@NotNull FullTaskTestingInfo value) {
        return new Object[]{
                value.getNameProject(),
                value.getNameSprint(),
                value.getNameTask(),
                value.getNameTaskTesting(),
                value.getFullNameUser(),
                value.getNameState(),
                value.getNamePriority(),
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
    public Class<FullTaskTestingInfo> resolveEntityClass() {
        return FullTaskTestingInfo.class;
    }
}
