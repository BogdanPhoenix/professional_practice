package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.entities.reference_book.AccessRight;
import org.university.entities.tables.Employee;
import org.university.entities.tables.EmployeeAccessRight;
import org.university.entities.tables.Project;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.business_logic.utils.reference_book.AccessRightUtil;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeAccessRightUtil extends TableModelView<EmployeeAccessRight> {
    private static final ObjectName PROJECT = new ObjectName("Проект", "project");
    private static final ObjectName EMPLOYEE = new ObjectName("Працівник", "employee");
    private static final ObjectName ACCESS_RIGHT = new ObjectName("Право доступу", "accessRight");

    public EmployeeAccessRightUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                EMPLOYEE.nameForUser(),
                ACCESS_RIGHT.nameForUser()
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
        Project project = (Project) table.getValueAt(indexRow, 0);
        Employee employee = (Employee) table.getValueAt(indexRow, 1);
        AccessRight accessRight = (AccessRight) table.getValueAt(indexRow, 2);

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.nameForSystem(), employee, SearchOperation.EQUAL),
                new SearchCriteria(ACCESS_RIGHT.nameForSystem(), accessRight, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null){
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(3, 1));

        panelBody.add(windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(EMPLOYEE, new EmployeeUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(ACCESS_RIGHT, new AccessRightUtil()));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        // TODO: 06.12.23 not required for this table
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull EmployeeAccessRight entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.nameForSystem(), entity.getEmployee(), SearchOperation.EQUAL),
                new SearchCriteria(ACCESS_RIGHT.nameForSystem(), entity.getAccessRight(), SearchOperation.EQUAL)
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

        windowComponent.updateComboBox(PROJECT, entity.getProject());
        windowComponent.updateComboBox(EMPLOYEE, entity.getEmployee());
        windowComponent.updateComboBox(ACCESS_RIGHT, entity.getAccessRight());
    }
}
