package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.Authentication;
import org.university.business_logic.tables.orm.Employee;
import org.university.business_logic.tables.orm.Position;

import java.util.List;

public class AuthenticationUtil extends TableModelView implements Select<Authentication> {
    public AuthenticationUtil(){
        titleColumns = List.of("Логін", "Посада", "Прізвище", "Ім'я");
        nameTable = "Зареєстровані користувачі";
    }

    @Override
    protected void addRows() {
        List<Authentication> authentications = selectAll();
        for (var authentication : authentications){
            Employee employee = authentication.getEmployee();
            Position position = employee.getPosition();
            model.addRow(new Object[]{
                    authentication.getLoginUser(),
                    position.getNamePosition(),
                    employee.getFirstName(),
                    employee.getNameUser()
            });
        }
    }

    @Override
    public Class<Authentication> resolveEntityClass() {
        return Authentication.class;
    }
}
