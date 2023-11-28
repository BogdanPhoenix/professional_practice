package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.SearchOperation;
import org.university.business_logic.tables.*;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.ui.realization.panel_interaction.SearchCriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeUtil extends TableModelView<Employee> {
    private static final String LOGIN_USER = "loginUser";
    private static final String PASSWORD = "passwordUser";
    private static final String POSITION = "position";
    private static final String FIRST_NAME = "firstName";
    private static final String NAME_USER = "nameUser";
    private static final String PHONE = "phoneNumber";
    private static final String IMAGE = "imageUser";
    private static final String DESCRIPTION = "description";

    public EmployeeUtil(){
        titleColumns = List.of("Логін", "Посада", "Прізвище",
                "Ім'я", "Номер телефону", "Фото", "Додаткова інформація");
        nameTable = "Працівники";
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var employees = selectAll();
        addRows(tableModel, employees);
    }

    @Override
    public Class<Employee> resolveEntityClass() {
        return Employee.class;
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull Employee value){
        Authentication authentication = value.getAuthentication();
        Position position = value.getPosition();

        return new Object[]{
                authentication.getLoginUser(),
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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

        panel.add(createTextFieldInputPanel("Логін", LOGIN_USER));
        panel.add(createPasswordFieldInputPanel("Пароль", PASSWORD));
        panel.add(createComboBoxPanel("Посада", POSITION, new PositionUtil()));
        panel.add(createTextFieldInputPanel("Прізвище", FIRST_NAME));
        panel.add(createTextFieldInputPanel("Ім'я", NAME_USER));
        panel.add(createTextFieldInputPanel("Номер телефону", PHONE));
        panel.add(createTextFieldInputPanel("Зображення", IMAGE));
        panel.add(createTextFieldInputPanel("Опис", DESCRIPTION));

        return createScrollPanel(panel);
    }

    @Override
    public ActionListener command() {
        return e -> {
            Position position = (Position) valueFromComboBox(POSITION);
            String firstName = valueFromTextField(FIRST_NAME);
            String nameUser = valueFromTextField(NAME_USER);

            var employee = Employee.builder()
                    .position(position)
                    .firstName(firstName)
                    .nameUser(nameUser)
                    .phoneNumber(valueFromTextField(PHONE))
                    .phoneNumber(valueFromTextField(IMAGE))
                    .description(valueFromTextField(DESCRIPTION))
                    .currentData(true)
                    .build();

            var authenticated = Authentication.builder()
                    .loginUser(valueFromTextField(LOGIN_USER))
                    .passwordUser(valueFromPasswordField(PASSWORD))
                    .currentData(true)
                    .build();

            SearchCriteria[] searchEmployee = {
                    new SearchCriteria(FIRST_NAME, firstName, SearchOperation.EQUAL),
                    new SearchCriteria(NAME_USER, nameUser, SearchOperation.EQUAL),
            };

            SearchCriteria searchAuthentication = new SearchCriteria(LOGIN_USER, authenticated.getLoginUser(), SearchOperation.EQUAL);

            AuthenticationUtil authentication = new AuthenticationUtil();

            if(!authentication.selectAll(searchAuthentication).isEmpty()){
                errorMessageWindow("Помилка логіну", "Користувач з таким логіном вже присутній в базі даних.");
                return;
            }

            var resultAdd = saveToTable(employee, searchEmployee);

            if(resultAdd.isEmpty()){
                return;
            }

            authenticated.setEmployee(resultAdd.get());
            authentication.save(authenticated);
        };
    }
}
