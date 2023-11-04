package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.CommandProject;

import java.util.List;

public class CommandProjectUtil extends TableModelView implements Select<CommandProject> {
    public CommandProjectUtil(){
        titleColumns = List.of("Проект", "Працівник");
        nameTable = "Команда проекту";
    }

    @Override
    protected void addRows() {
        List<CommandProject> projects = selectAll();
        for (var project : projects) {
            model.addRow(new Object[]{
                    project.getProject().getNameProject(),
                    project.getEmployee().getFirstName() + " " + project.getEmployee().getNameUser()
            });
        }
    }

    @Override
    public Class<CommandProject> resolveEntityClass() {
        return CommandProject.class;
    }
}
