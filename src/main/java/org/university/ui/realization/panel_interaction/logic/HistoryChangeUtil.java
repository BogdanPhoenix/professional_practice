package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.tables.*;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class HistoryChangeUtil extends TableModelView<HistoryChange> {
    public HistoryChangeUtil(){
        titleColumns = List.of("Проект", "Тип зміни", "Виконавець", "Назва", "Дата створення");
        nameTable = "Історія змін";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var historyChanges = selectAll();
        addRows(tableModel, historyChanges);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull HistoryChange value){
        Project project = value.getProject();
        Employee performer = value.getPerformer();
        TypeChange typeChange = value.getTypeChange();

        return new Object[]{
                project.getNameProject(),
                typeChange.getNameTypeChange(),
                performer.getFirstName() + " " + performer.getNameUser(),
                value.getNameChange(),
                value.getDateTimeCreate()
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
    public Class<HistoryChange> resolveEntityClass() {
        return HistoryChange.class;
    }
}
