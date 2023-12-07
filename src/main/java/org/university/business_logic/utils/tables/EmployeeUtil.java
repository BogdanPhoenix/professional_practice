package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.utils.reference_book.PositionUtil;
import org.university.entities.reference_book.Position;
import org.university.entities.tables.Employee;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EmployeeUtil extends TableModelView<Employee> {
    private static final ObjectName LOGIN_USER = new ObjectName("Логін", "loginUser");
    private static final ObjectName PASSWORD = new ObjectName("Пароль", "passwordUser");
    private static final ObjectName POSITION = new ObjectName("Посада", "position");
    private static final ObjectName FIRST_NAME = new ObjectName("Прізвище", "firstName");
    private static final ObjectName NAME_USER = new ObjectName("Ім'я", "nameUser");
    private static final ObjectName PHONE = new ObjectName("Номер телефону", "phoneNumber");
    private static final ObjectName IMAGE = new ObjectName("Зображення", "imageUser");
    private static final ObjectName DESCRIPTION = new ObjectName("Опис", "description");

    public EmployeeUtil(){
        titleColumns = List.of(
                LOGIN_USER.nameForUser(),
                POSITION.nameForUser(),
                FIRST_NAME.nameForUser(),
                NAME_USER.nameForUser(),
                PHONE.nameForUser(),
                IMAGE.nameForUser(),
                DESCRIPTION.nameForUser()
        );
        nameTable = "Працівники";
    }

    @Override
    public Class<Employee> resolveEntityClass() {
        return Employee.class;
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull Employee value){
        return new Object[]{
                value.getLoginUser(),
                value.getPosition(),
                value.getFirstName(),
                value.getNameUser(),
                value.getPhoneNumber(),
                value.getImageUser(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        String login = (String) table.getValueAt(indexRow, 0);
        String firstName = (String) table.getValueAt(indexRow, 2);
        String nameUser = (String) table.getValueAt(indexRow, 3);

        return new SearchCriteria[] {
                new SearchCriteria(LOGIN_USER.nameForSystem(), login, SearchOperation.EQUAL),
                new SearchCriteria(FIRST_NAME.nameForSystem(), firstName, SearchOperation.EQUAL),
                new SearchCriteria(NAME_USER.nameForSystem(), nameUser, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null){
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(8, 1));

        panelBody.add(windowComponent.createTextFieldInputPanel(LOGIN_USER));
        panelBody.add(windowComponent.createPasswordFieldInputPanel(PASSWORD));
        panelBody.add(windowComponent.createComboBoxPanel(POSITION, new PositionUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(FIRST_NAME));
        panelBody.add(windowComponent.createTextFieldInputPanel(NAME_USER));
        panelBody.add(windowComponent.createTextFieldInputPanel(PHONE));
        panelBody.add(windowComponent.createTextFieldInputPanel(IMAGE));
        panelBody.add(windowComponent.createTextFieldInputPanel(DESCRIPTION));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() {
        String login = valueFromTextField(LOGIN_USER);
        String password = valueFromPasswordField(PASSWORD);

        if(login.isEmpty() || password.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s",
                    LOGIN_USER.nameForUser(), PASSWORD.nameForUser()
            ));
        }

        String firstName = valueFromTextField(FIRST_NAME);
        String nameUser = valueFromTextField(NAME_USER);
        String phoneNumber = valueFromTextField(PHONE);

        if(firstName.isEmpty() || nameUser.isEmpty() || phoneNumber.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    FIRST_NAME.nameForUser(), NAME_USER.nameForUser(), PHONE.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Employee entity){
        return new SearchCriteria[] {
                new SearchCriteria(LOGIN_USER.nameForSystem(), entity.getLoginUser(), SearchOperation.EQUAL),
                new SearchCriteria(FIRST_NAME.nameForSystem(), entity.getFirstName(), SearchOperation.EQUAL),
                new SearchCriteria(NAME_USER.nameForSystem(), entity.getNameUser(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Employee newEntity() {
        try {
            String login = valueFromTextField(LOGIN_USER);
            String password = valueFromPasswordField(PASSWORD);
            Position position = (Position) valueFromComboBox(POSITION);
            String firstName = valueFromTextField(FIRST_NAME);
            String nameUser = valueFromTextField(NAME_USER);
            String phoneNumber = valueFromTextField(PHONE);
            String imageUser = valueFromTextField(IMAGE);
            String description = valueFromTextField(DESCRIPTION);

            return Employee.builder()
                    .loginUser(login)
                    .passwordUser(password)
                    .position(position)
                    .firstName(firstName)
                    .nameUser(nameUser)
                    .phoneNumber(phoneNumber)
                    .imageUser(imageUser.getBytes())
                    .description(description)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    public void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();

        windowComponent.updateTextField(LOGIN_USER, entity.getLoginUser());
        windowComponent.updatePasswordField(PASSWORD, entity.getPasswordUser());
        windowComponent.updateComboBox(POSITION, entity.getPosition());
        windowComponent.updateTextField(FIRST_NAME, entity.getFirstName());
        windowComponent.updateTextField(NAME_USER, entity.getNameUser());
        windowComponent.updateTextField(PHONE, entity.getPhoneNumber());
        windowComponent.updateTextField(IMAGE, new String(entity.getImageUser(), StandardCharsets.UTF_8));
        windowComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }
}
