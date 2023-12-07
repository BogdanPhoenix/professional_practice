package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.utils.reference_book.ExecutionStatusUtil;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.tables.Employee;
import org.university.entities.tables.Project;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ProjectUtil extends TableModelView<Project> {
    private static final ObjectName PROJECT = new ObjectName("Назва проекту", "nameProject");
    private static final ObjectName INTERMEDIARY = new ObjectName("Відповідальна особа", "intermediary");
    private static final ObjectName EXECUTION = new ObjectName("Стан виконання", "executionStatus");
    private static final ObjectName DATE_START = new ObjectName("Дата старту проекту", "dateTimeStart");
    private static final ObjectName PLANNED_COMPLETION = new ObjectName("Запланована дата закінчення", "plannedCompletionDate");
    private static final ObjectName DATE_END = new ObjectName("Дата закінчення", "dateTimeEnd");
    private static final ObjectName BUDGET = new ObjectName("Бюджет", "budget");
    private static final ObjectName CLIENT_INFO = new ObjectName("Інформація про замовника", "currentData");
    private static final ObjectName DESCRIPTION = new ObjectName("Опис проекту", "description");

    public ProjectUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                INTERMEDIARY.nameForUser(),
                EXECUTION.nameForUser(),
                DATE_START.nameForUser(),
                PLANNED_COMPLETION.nameForUser(),
                DATE_END.nameForUser(),
                BUDGET.nameForUser(),
                CLIENT_INFO.nameForUser(),
                DESCRIPTION.nameForUser()
        );
        nameTable = "Проекти";
    }

    @Override
    public Class<Project> resolveEntityClass() {
        return Project.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull Project value) {
        return new Object[]{
                value.getNameProject(),
                value.getIntermediary(),
                value.getExecutionStatus(),
                value.getDateTimeStart(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getBudget(),
                value.getClientInfo(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        String nameProject = (String) table.getValueAt(indexRow, 0);
        return new SearchCriteria[] { new SearchCriteria(PROJECT.nameForSystem(), nameProject, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(9, 1));

        panelBody.add(windowComponent.createTextFieldInputPanel(PROJECT));
        panelBody.add(windowComponent.createComboBoxPanel(INTERMEDIARY, new EmployeeUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(EXECUTION, new ExecutionStatusUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_START));
        panelBody.add(windowComponent.createTextFieldInputPanel(PLANNED_COMPLETION));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_END));
        panelBody.add(windowComponent.createTextFieldInputPanel(BUDGET));
        panelBody.add(windowComponent.createTextFieldInputPanel(CLIENT_INFO));
        panelBody.add(windowComponent.createTextFieldInputPanel(DESCRIPTION));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameProject = valueFromTextField(PROJECT);
        String dateStart = valueFromTextField(DATE_START);
        String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
        String budget = valueFromTextField(BUDGET);

        if(nameProject.isEmpty() || dateStart.isEmpty() || plannedCompletion.isEmpty() || budget.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s %n%s",
                    PROJECT.nameForUser(), DATE_START.nameForUser(),
                    PLANNED_COMPLETION.nameForUser(), BUDGET.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Project entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), entity.getNameProject(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Project newEntity() {
        try {
            String nameProject = valueFromTextField(PROJECT);
            Employee intermediary = (Employee) valueFromComboBox(INTERMEDIARY);
            ExecutionStatus executionStatus = (ExecutionStatus) valueFromComboBox(EXECUTION);
            String dateStart = valueFromTextField(DATE_START);
            String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
            String dateEndString = valueFromTextField(DATE_END);
            String budget = valueFromTextField(BUDGET);
            String clientInfo = valueFromTextField(CLIENT_INFO);
            String description = valueFromTextField(DESCRIPTION);

            Timestamp dateEnd = convertFromStringToTimestamp(dateEndString);

            return Project.builder()
                    .executionStatus(executionStatus)
                    .intermediary(intermediary)
                    .nameProject(nameProject)
                    .dateTimeStart(Timestamp.valueOf(dateStart))
                    .plannedCompletionDate(Timestamp.valueOf(plannedCompletion))
                    .dateTimeEnd(dateEnd)
                    .budget(new BigDecimal(budget))
                    .description(description)
                    .clientInfo(clientInfo)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();

        String dateEnd = convertFromTimestampToString(entity.getDateTimeEnd());

        windowComponent.updateTextField(PROJECT, entity.getNameProject());
        windowComponent.updateComboBox(INTERMEDIARY, entity.getIntermediary());
        windowComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());
        windowComponent.updateTextField(DATE_START, entity.getDateTimeStart().toString());
        windowComponent.updateTextField(PLANNED_COMPLETION, entity.getPlannedCompletionDate().toString());
        windowComponent.updateTextField(DATE_END, dateEnd);
        windowComponent.updateTextField(BUDGET, entity.getBudget().toString());
        windowComponent.updateTextField(CLIENT_INFO, entity.getClientInfo());
        windowComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }
}
