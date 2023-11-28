package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.tables.*;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeAccessRightUtil extends TableModelView<EmployeeAccessRight> {
    public EmployeeAccessRightUtil(){
        titleColumns = List.of("Проект", "Працівник", "Право доступу");
        nameTable = "Права доступу працівника";
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var employeeAccessRights = selectAll();
        addRows(tableModel, employeeAccessRights);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull EmployeeAccessRight value){
        Project project = value.getProject();
        Employee performer = value.getEmployee();
        AccessRight accessRight = value.getAccessRight();

        return new Object[]{
                project.getNameProject(),
                performer.getFirstName() + " " + performer.getNameUser(),
                accessRight.getNameRight()
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
    public Class<EmployeeAccessRight> resolveEntityClass() {
        return EmployeeAccessRight.class;
    }
}
