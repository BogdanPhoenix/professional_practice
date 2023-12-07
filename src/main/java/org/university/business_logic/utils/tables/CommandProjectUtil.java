package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.entities.tables.Employee;
import org.university.entities.tables.Project;
import org.university.business_logic.abstracts.TableModelView;
import org.university.entities.tables.CommandProject;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CommandProjectUtil extends TableModelView<CommandProject> {
    private static final ObjectName PROJECT = new ObjectName("Проект", "project");
    private static final ObjectName EMPLOYEE = new ObjectName("Працівник", "employee");

    public CommandProjectUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                EMPLOYEE.nameForUser()
        );
        nameTable = "Команда проекту";
    }

    @Override
    public Class<CommandProject> resolveEntityClass() {
        return CommandProject.class;
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull CommandProject value){
        return new Object[]{
                value.getProject(),
                value.getEmployee()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        Project project = (Project) table.getValueAt(indexRow, 0);
        Employee employee = (Employee) table.getValueAt(indexRow, 1);

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.nameForSystem(), employee, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null){
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(2,1));

        panelBody.add(windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(EMPLOYEE, new EmployeeUtil()));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        // TODO: 06.12.23 not required for this table
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull CommandProject entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.nameForSystem(), entity.getEmployee(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected CommandProject newEntity() {
        try {
            Project project = (Project) valueFromComboBox(PROJECT);
            Employee employee = (Employee) valueFromComboBox(EMPLOYEE);

            return CommandProject.builder()
                    .project(project)
                    .employee(employee)
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
    }
}
