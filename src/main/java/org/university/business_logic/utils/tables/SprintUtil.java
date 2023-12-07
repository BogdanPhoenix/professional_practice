package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.utils.reference_book.ExecutionStatusUtil;
import org.university.business_logic.utils.reference_book.TypeComplexityUtil;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.TypeComplexity;
import org.university.entities.tables.Project;
import org.university.entities.tables.Sprint;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class SprintUtil extends TableModelView<Sprint> {
    private static final ObjectName PROJECT = new ObjectName("Назва проекту", "project");
    private static final ObjectName SPRINT = new ObjectName("Назва спринту", "nameSprint");
    private static final ObjectName EXECUTION = new ObjectName("Стан виконання", "executionStatus");
    private static final ObjectName COMPLEXITY = new ObjectName("Тип складності", "complexity");
    private static final ObjectName DATE_START = new ObjectName("Дата початку", "dateTimeCreate");
    private static final ObjectName PLANNED_COMPLETION = new ObjectName("Запланована дата закінчення", "plannedCompletionDate");
    private static final ObjectName DATE_END = new ObjectName("Дата закінчення", "dateTimeEnd");
    private static final ObjectName DESCRIPTION = new ObjectName("Опис", "description");

    public SprintUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                SPRINT.nameForUser(),
                EXECUTION.nameForUser(),
                COMPLEXITY.nameForUser(),
                DATE_START.nameForUser(),
                PLANNED_COMPLETION.nameForUser(),
                DATE_END.nameForUser(),
                DESCRIPTION.nameForUser()
        );
        nameTable = "Спринти";
    }

    @Override
    public Class<Sprint> resolveEntityClass() {
        return Sprint.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull Sprint value) {
        return new Object[]{
                value.getProject(),
                value.getNameSprint(),
                value.getExecutionStatus(),
                value.getComplexity(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        Project project = (Project) table.getValueAt(indexRow, 0);
        String nameSprint = (String) table.getValueAt(indexRow, 1);

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(SPRINT.nameForSystem(), nameSprint, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(8, 1));

        panelBody.add(windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(SPRINT));
        panelBody.add(windowComponent.createComboBoxPanel(EXECUTION, new ExecutionStatusUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(COMPLEXITY, new TypeComplexityUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_START));
        panelBody.add(windowComponent.createTextFieldInputPanel(PLANNED_COMPLETION));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_END));
        panelBody.add(windowComponent.createTextFieldInputPanel(DESCRIPTION));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameSprint = valueFromTextField(SPRINT);
        String dateStart = valueFromTextField(DATE_START);
        String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);

        if(nameSprint.isEmpty() || dateStart.isEmpty() || plannedCompletion.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    SPRINT.nameForUser(), DATE_START.nameForUser(), PLANNED_COMPLETION.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Sprint entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(SPRINT.nameForSystem(), entity.getNameSprint(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Sprint newEntity() {
        try {
            Project project = (Project) valueFromComboBox(PROJECT);
            String nameSprint = valueFromTextField(SPRINT);
            ExecutionStatus executionStatus = (ExecutionStatus) valueFromComboBox(EXECUTION);
            TypeComplexity typeComplexity = (TypeComplexity) valueFromComboBox(COMPLEXITY);
            String dateStart = valueFromTextField(DATE_START);
            String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
            String dateEndString = valueFromTextField(DATE_END);
            String description = valueFromTextField(DESCRIPTION);

            Timestamp dateEnd = convertFromStringToTimestamp(dateEndString);

            return Sprint.builder()
                    .project(project)
                    .nameSprint(nameSprint)
                    .executionStatus(executionStatus)
                    .complexity(typeComplexity)
                    .dateTimeCreate(Timestamp.valueOf(dateStart))
                    .plannedCompletionDate(Timestamp.valueOf(plannedCompletion))
                    .dateTimeEnd(dateEnd)
                    .description(description)
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

        windowComponent.updateComboBox(PROJECT, entity.getProject());
        windowComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());
        windowComponent.updateComboBox(COMPLEXITY, entity.getComplexity());

        windowComponent.updateTextField(SPRINT, entity.getNameSprint());
        windowComponent.updateTextField(DATE_START, entity.getDateTimeCreate().toString());
        windowComponent.updateTextField(PLANNED_COMPLETION, entity.getPlannedCompletionDate().toString());
        windowComponent.updateTextField(DATE_END, dateEnd);
        windowComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }
}
