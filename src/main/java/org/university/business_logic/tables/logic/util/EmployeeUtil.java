package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.Employee;
import org.university.business_logic.tables.orm.Position;

import java.util.List;

public class EmployeeUtil extends TableModelView implements Select<Employee> {
    public EmployeeUtil(){
        titleColumns = List.of("Посада", "Прізвище", "Ім'я", "Номер телефону", "Фото", "Додаткова інформація");
        nameTable = "Працівники";
    }

    @Override
    protected void addRows() {
        List<Employee> employees = selectAll();
        for (var employee : employees){
            Position position = employee.getPosition();
            model.addRow(new Object[]{
                    position.getNamePosition(),
                    employee.getFirstName(),
                    employee.getNameUser(),
                    employee.getPhoneNumber(),
                    employee.getImageUser(),
                    employee.getDescription()
            });
        }
    }

    @Override
    public Class<Employee> resolveEntityClass() {
        return Employee.class;
    }
}
