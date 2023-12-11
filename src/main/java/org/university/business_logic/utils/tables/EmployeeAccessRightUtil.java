package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.entities.reference_book.AccessRight;
import org.university.entities.tables.Employee;
import org.university.entities.tables.EmployeeAccessRight;
import org.university.entities.tables.Project;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeAccessRightUtil extends TableModelView<EmployeeAccessRight> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Проект", "project", Project.class);
    private static final AttributeName EMPLOYEE = new AttributeNameComboBox(1, "Працівник", "employee", Employee.class);
    private static final AttributeName ACCESS_RIGHT = new AttributeNameComboBox(2, "Право доступу", "accessRight", AccessRight.class);

    public EmployeeAccessRightUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                EMPLOYEE.getNameForUser(),
                ACCESS_RIGHT.getNameForUser()
        );
        nameTable = "Права доступу працівника";
    }

    @Override
    public Class<EmployeeAccessRight> resolveEntityClass() {
        return EmployeeAccessRight.class;
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull EmployeeAccessRight value){
        return new Object[]{
                value.getProject(),
                value.getEmployee(),
                value.getAccessRight()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var project = table.getValueAt(indexRow, PROJECT.getId());
        var employee = table.getValueAt(indexRow, EMPLOYEE.getId());
        var accessRight = table.getValueAt(indexRow, ACCESS_RIGHT.getId());

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.getNameForSystem(), employee, SearchOperation.EQUAL),
                new SearchCriteria(ACCESS_RIGHT.getNameForSystem(), accessRight, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        return createPanel(false);
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        // TODO: 06.12.23 not required for this table
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull EmployeeAccessRight entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.getNameForSystem(), entity.getEmployee(), SearchOperation.EQUAL),
                new SearchCriteria(ACCESS_RIGHT.getNameForSystem(), entity.getAccessRight(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected EmployeeAccessRight newEntity() {
        try {
            Project project = (Project) valueFromComboBox(PROJECT);
            Employee employee = (Employee) valueFromComboBox(EMPLOYEE);
            AccessRight accessRight = (AccessRight) valueFromComboBox(ACCESS_RIGHT);

            return EmployeeAccessRight.builder()
                    .project(project)
                    .employee(employee)
                    .accessRight(accessRight)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();

        panelComponent.updateComboBox(PROJECT, entity.getProject());
        panelComponent.updateComboBox(EMPLOYEE, entity.getEmployee());
        panelComponent.updateComboBox(ACCESS_RIGHT, entity.getAccessRight());
    }

    @Override
    public JPanel selectEntryPanel() {
        return createPanel(true);
    }

    private JPanel createPanel(boolean flag){
        clearPanelBody();

        List<JPanel> components = new ArrayList<>(createComboBoxPanels(flag, PROJECT, EMPLOYEE, ACCESS_RIGHT));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        return new ArrayList<>(criteriaFromComboBox(PROJECT, EMPLOYEE, ACCESS_RIGHT));
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, EMPLOYEE, ACCESS_RIGHT);
            createGraphUI(variants);
        };
    }
}
