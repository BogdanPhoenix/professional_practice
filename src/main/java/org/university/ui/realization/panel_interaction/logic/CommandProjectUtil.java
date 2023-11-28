package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.CommandProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class CommandProjectUtil extends TableModelView<CommandProject> {
    public CommandProjectUtil(){
        titleColumns = List.of("Проект", "Працівник");
        nameTable = "Команда проекту";
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var projects = selectAll();
        addRows(tableModel, projects);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull CommandProject value){
        return new Object[]{
                value.getProject().getNameProject(),
                value.getEmployee().getFirstName() + " " + value.getEmployee().getNameUser()
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
    public Class<CommandProject> resolveEntityClass() {
        return CommandProject.class;
    }
}
