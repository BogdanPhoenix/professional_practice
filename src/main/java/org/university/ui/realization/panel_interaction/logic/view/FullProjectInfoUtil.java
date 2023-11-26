package org.university.ui.realization.panel_interaction.logic.view;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.view.FullProjectInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class FullProjectInfoUtil extends TableModelView<FullProjectInfo> {
    public FullProjectInfoUtil(){
        titleColumns = List.of("Назва проекту", "Відповідальна особа", "Стан виконання", "Дата старту проекту", "Запланована дата закінчення", "Дата закінчення",
                "Бюджет", "Інформація про замовника", "Опис проекту");
        nameTable = "Основна інформація про проекти";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var fullProjectInfos = selectAll();
        addRows(tableModel, fullProjectInfos);
    }

    @Override
    protected Object[] createAttribute(@NotNull FullProjectInfo value) {
        return new Object[]{
                value.getNameProject(),
                value.getFullUserName(),
                value.getNameExecution(),
                value.getDateTimeStart(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getBudget(),
                value.getClientIndo(),
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
    public Class<FullProjectInfo> resolveEntityClass() {
        return FullProjectInfo.class;
    }
}
