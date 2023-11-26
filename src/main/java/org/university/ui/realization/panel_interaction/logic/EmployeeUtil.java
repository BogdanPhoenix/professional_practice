package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.tables.*;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeUtil extends TableModelView<Employee> {
    public EmployeeUtil(){
        titleColumns = List.of("Посада", "Прізвище", "Ім'я", "Номер телефону", "Фото", "Додаткова інформація");
        nameTable = "Працівники";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var employees = selectAll();
        addRows(tableModel, employees);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull Employee value){
        Position position = value.getPosition();

        return new Object[]{
                position.getNamePosition(),
                value.getFirstName(),
                value.getNameUser(),
                value.getPhoneNumber(),
                value.getImageUser(),
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
    public Class<Employee> resolveEntityClass() {
        return Employee.class;
    }
}
