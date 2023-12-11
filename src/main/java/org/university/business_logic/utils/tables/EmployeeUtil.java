package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.entities.reference_book.Position;
import org.university.entities.tables.Employee;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.search_tools.SearchCriteria;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeUtil extends TableModelView<Employee> {
    private static final AttributeName LOGIN_USER = new AttributeNameSimple(0, "Логін", "loginUser");
    private static final AttributeName POSITION = new AttributeNameComboBox(1, "Посада", "position", Position.class);
    private static final AttributeName FIRST_NAME = new AttributeNameSimple(2, "Прізвище", "firstName");
    private static final AttributeName NAME_USER = new AttributeNameSimple(3, "Ім'я", "nameUser");
    private static final AttributeName PHONE = new AttributeNameSimple(4, "Номер телефону", "phoneNumber");
    private static final AttributeName IMAGE = new AttributeNameSimple(5, "Зображення", "imageUser");
    private static final AttributeName DESCRIPTION = new AttributeNameSimple(6, "Опис", "description");
    private static final AttributeName PASSWORD = new AttributeNameSimple(7, "Пароль", "passwordUser");

    public EmployeeUtil(){
        titleColumns = List.of(
                LOGIN_USER.getNameForUser(),
                POSITION.getNameForUser(),
                FIRST_NAME.getNameForUser(),
                NAME_USER.getNameForUser(),
                PHONE.getNameForUser(),
                IMAGE.getNameForUser(),
                DESCRIPTION.getNameForUser()
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
        var login = table.getValueAt(indexRow, LOGIN_USER.getId());
        var firstName = table.getValueAt(indexRow, FIRST_NAME.getId());
        var nameUser = table.getValueAt(indexRow, NAME_USER.getId());

        return new SearchCriteria[] {
                new SearchCriteria(LOGIN_USER.getNameForSystem(), login, SearchOperation.EQUAL),
                new SearchCriteria(FIRST_NAME.getNameForSystem(), firstName, SearchOperation.EQUAL),
                new SearchCriteria(NAME_USER.getNameForSystem(), nameUser, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        return createPanel(false);
    }

    @Override
    protected void checkCompletenessFields() {
        String login = valueFromTextField(LOGIN_USER);
        String password = valueFromTextField(PASSWORD);

        if(login.isEmpty() || password.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s",
                    LOGIN_USER.getNameForUser(), PASSWORD.getNameForUser()
            ));
        }

        String firstName = valueFromTextField(FIRST_NAME);
        String nameUser = valueFromTextField(NAME_USER);
        String phoneNumber = valueFromTextField(PHONE);

        if(firstName.isEmpty() || nameUser.isEmpty() || phoneNumber.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    FIRST_NAME.getNameForUser(), NAME_USER.getNameForUser(), PHONE.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Employee entity){
        return new SearchCriteria[] {
                new SearchCriteria(LOGIN_USER.getNameForSystem(), entity.getLoginUser(), SearchOperation.EQUAL),
                new SearchCriteria(FIRST_NAME.getNameForSystem(), entity.getFirstName(), SearchOperation.EQUAL),
                new SearchCriteria(NAME_USER.getNameForSystem(), entity.getNameUser(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Employee newEntity() {
        try {
            String login = valueFromTextField(LOGIN_USER);
            String password = valueFromTextField(PASSWORD);
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

        panelComponent.updateTextField(LOGIN_USER, entity.getLoginUser());
        panelComponent.updatePasswordField(PASSWORD, entity.getPasswordUser());
        panelComponent.updateComboBox(POSITION, entity.getPosition());
        panelComponent.updateTextField(FIRST_NAME, entity.getFirstName());
        panelComponent.updateTextField(NAME_USER, entity.getNameUser());
        panelComponent.updateTextField(PHONE, entity.getPhoneNumber());
        panelComponent.updateTextField(IMAGE, new String(entity.getImageUser(), StandardCharsets.UTF_8));
        panelComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }

    @Override
    public JPanel selectEntryPanel() {
        return createPanel(true);
    }

    private JPanel createPanel(boolean flag){
        clearPanelBody();

        List<JPanel> components = new ArrayList<>();
        components.addAll(createComboBoxPanels(flag, POSITION));
        components.addAll(createTextFieldPanels(flag, names(flag)));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Contract(value = "_ -> new", pure = true)
    private AttributeName @NotNull [] names(boolean flag){
        if(flag){
            return new AttributeName[] {LOGIN_USER, FIRST_NAME, NAME_USER, PHONE};
        }
        else {
            return new AttributeName[] {LOGIN_USER, PASSWORD, FIRST_NAME, NAME_USER, PHONE, IMAGE, DESCRIPTION};
        }
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromComboBox(POSITION));
        searchCriteria.addAll(criteriaTextField(LOGIN_USER, FIRST_NAME, NAME_USER, PHONE));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(POSITION);
            createGraphUI(variants);
        };
    }
}
