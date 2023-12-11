package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.entities.tables.Employee;
import org.university.entities.tables.Project;
import org.university.entities.tables.CommandProject;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandProjectUtil extends TableModelView<CommandProject> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Проект", "project", Project.class);
    private static final AttributeName EMPLOYEE = new AttributeNameComboBox(1, "Працівник", "employee", Employee.class);

    public CommandProjectUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                EMPLOYEE.getNameForUser()
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
        var project = table.getValueAt(indexRow, PROJECT.getId());
        var employee = table.getValueAt(indexRow, EMPLOYEE.getId());

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.getNameForSystem(), employee, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        return createPanel(false);
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException { /* TODO not required for this table */ }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull CommandProject entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(EMPLOYEE.getNameForSystem(), entity.getEmployee(), SearchOperation.EQUAL)
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

        panelComponent.updateComboBox(PROJECT, entity.getProject());
        panelComponent.updateComboBox(EMPLOYEE, entity.getEmployee());
    }

    @Override
    public JPanel selectEntryPanel() {
        return createPanel(true);
    }

    private JPanel createPanel(boolean flag){
        clearPanelBody();

        List<JPanel> components = new ArrayList<>(createComboBoxPanels(flag, PROJECT, EMPLOYEE));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        return new ArrayList<>(criteriaFromComboBox(PROJECT, EMPLOYEE));
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, EMPLOYEE);
            createGraphUI(variants);
        };
    }
}
