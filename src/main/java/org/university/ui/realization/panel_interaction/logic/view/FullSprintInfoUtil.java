package org.university.ui.realization.panel_interaction.logic.view;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.view.FullSprintInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class FullSprintInfoUtil extends TableModelView<FullSprintInfo> {
    public FullSprintInfoUtil(){
        titleColumns = List.of("Назва проекту", "Назва спринту", "Стан виконання", "Тип складності", "Дата початку", "Запланована дата закінчення", "Дата закінчення", "Опис");
        nameTable = "Основна інформація про спринт";
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var fullSprintInfos = selectAll();
        addRows(tableModel, fullSprintInfos);
    }

    @Override
    protected Object[] createAttribute(@NotNull FullSprintInfo value) {
        return new Object[]{
                value.getNameProject(),
                value.getNameSprint(),
                value.getNameExecution(),
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
    public Class<FullSprintInfo> resolveEntityClass() {
        return FullSprintInfo.class;
    }
}
