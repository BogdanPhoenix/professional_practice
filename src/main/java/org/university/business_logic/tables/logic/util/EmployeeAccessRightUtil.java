package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.*;

import java.util.List;

public class EmployeeAccessRightUtil extends TableModelView implements Select<EmployeeAccessRight> {
    public EmployeeAccessRightUtil(){
        titleColumns = List.of("Проект", "Працівник", "Право доступу");
        nameTable = "Права доступу працівника";
    }

    @Override
    protected void addRows() {
        List<EmployeeAccessRight> employeeAccessRights = selectAll();
        for (var employeeAccessRight : employeeAccessRights) {
            Project project = employeeAccessRight.getProject();
            Employee performer = employeeAccessRight.getEmployee();
            AccessRight accessRight = employeeAccessRight.getAccessRight();

            model.addRow(new Object[]{
                    project.getNameProject(),
                    performer.getFirstName() + " " + performer.getNameUser(),
                    accessRight.getNameRight()
            });
        }
    }

    @Override
    public Class<EmployeeAccessRight> resolveEntityClass() {
        return EmployeeAccessRight.class;
    }
}
