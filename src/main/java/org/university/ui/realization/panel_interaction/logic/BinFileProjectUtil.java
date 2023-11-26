package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.BinFileProject;
import org.university.business_logic.tables.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class BinFileProjectUtil extends TableModelView<BinFileProject> {
    public BinFileProjectUtil(){
        titleColumns = List.of("Проект", "Виконавець", "Розширення файлу", "Назва файлу", "Файл", "Дата завантаження", "Опис");
        nameTable = "Бінарні файли";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var binFileProjects = selectAll();
        addRows(tableModel, binFileProjects);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull BinFileProject value){
        Employee performer = value.getPerformer();

        return new Object[]{
                value.getProject().getNameProject(),
                performer.getFirstName() + " " + performer.getNameUser(),
                value.getFileExtension().getNameExtension(),
                value.getNameFile(),
                value.getDateTimeDown(),
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
    public Class<BinFileProject> resolveEntityClass() {
        return BinFileProject.class;
    }
}
