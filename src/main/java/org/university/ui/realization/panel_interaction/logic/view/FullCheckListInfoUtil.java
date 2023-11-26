package org.university.ui.realization.panel_interaction.logic.view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.view.FullCheckListInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class FullCheckListInfoUtil extends TableModelView<FullCheckListInfo> {
    public FullCheckListInfoUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Відповідальний", "Виконання", "Пріоритет", "Дата старту виконання", "Запланована дата завершення", "Дата завершення", "Опис");
        nameTable = "Підзавдання";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var fullCheckListInfos = selectAll();
        addRows(tableModel, fullCheckListInfos);
    }

    @Override
    @Contract("_ -> new")
    protected Object @NotNull [] createAttribute(@NotNull FullCheckListInfo value){
        return new Object[]{
                value.getNameProject(),
                value.getNameSprint(),
                value.getNameTask(),
                value.getNameCheckList(),
                value.getFullNameUser(),
                value.getNameExecution(),
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
    public Class<FullCheckListInfo> resolveEntityClass() {
        return FullCheckListInfo.class;
    }
}
