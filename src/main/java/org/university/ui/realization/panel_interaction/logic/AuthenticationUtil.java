package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.Authentication;
import org.university.business_logic.tables.Employee;
import org.university.business_logic.tables.Position;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class AuthenticationUtil extends TableModelView<Authentication> {
    public AuthenticationUtil(){
        titleColumns = List.of("Логін", "Посада", "Прізвище", "Ім'я");
        nameTable = "Зареєстровані користувачі";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var authentications = selectAll();
        addRows(tableModel, authentications);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull Authentication value){
        Employee employee = value.getEmployee();
        Position position = employee.getPosition();

        return new Object[]{
                value.getLoginUser(),
                position.getNamePosition(),
                employee.getFirstName(),
                employee.getNameUser()
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
    public Class<Authentication> resolveEntityClass() {
        return Authentication.class;
    }
}
